package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.activity.adapter.CommentListAdapter;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.comment.CommentRequestDto;
import com.example.turibuildtest0405.dto.comment.PostCommentDto;
import com.example.turibuildtest0405.dto.post.PostDetailDto;
import com.example.turibuildtest0405.util.turiapi.RetrofitService;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity {
    ImageView ivProfileImage, ivPostImage;
    TextView tvNickname, tvContent;
    ListView listView;

    ImageView ivCommentProfileImage;
    EditText edtCommentContent;
    Button btnCommentSubmit;

    ImageButton imgBtnMenu;

    CommentListAdapter adapter;

    RetrofitService retrofitService;

    Long postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        retrofitService = RetrofitService.getInstance(getApplicationContext());

        ivProfileImage = findViewById(R.id.DivProfile);
        ivPostImage = findViewById(R.id.DivPostImage);
        tvNickname = findViewById(R.id.tv_email);
        tvContent = findViewById(R.id.DtvContent);
        ivCommentProfileImage = findViewById(R.id.divCommentMyProfile);
        edtCommentContent = findViewById(R.id.edtCommentContent);
        btnCommentSubmit = findViewById(R.id.btnCommentSubmit);
        imgBtnMenu = findViewById(R.id.imgbtn_Menu);
        listView = findViewById(R.id.DlistView);

        adapter = new CommentListAdapter();

        listView.setAdapter(adapter);

        Intent intent = getIntent();
        PostDetailDto dto = (PostDetailDto) intent.getSerializableExtra("postDetailDto");
        postId = dto.getPostId();

        Glide.with(this).load(dto.getProfileImageUrl()).into(ivProfileImage);
        Glide.with(this).load(dto.getPostImageUrl()).into(ivPostImage);
        tvNickname.setText(dto.getNickname());
        tvContent.setText(dto.getContent());

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        String myProfileImageUrl = preferences.getString("profileImageUrl", "");
        String myNickname = preferences.getString("nickname", "");
        if(StringUtils.isNotEmpty(myProfileImageUrl)) {
            Glide.with(this).load(myProfileImageUrl).into(ivCommentProfileImage);
        }
        if(checkAuthor(dto.getEmail())) {
            imgBtnMenu.setVisibility(View.VISIBLE);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyPad();
            }
        }, 100);

        edtCommentContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    btnCommentSubmit.setVisibility(View.VISIBLE);
                } else {
                    btnCommentSubmit.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnCommentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentContent = edtCommentContent.getText().toString();
                if(commentContent.equals("")) {
                    Toast.makeText(getApplicationContext(), "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                CommentRequestDto commentRequestDto = new CommentRequestDto(postId, commentContent);

                retrofitService.commentApi.create(commentRequestDto).enqueue(new Callback<ResponseDto>() {
                    @Override
                    public void onResponse(Call<ResponseDto> call, Response<ResponseDto> response) {
                        ResponseDto responseDto = response.body();

                        PostCommentDto comment = new PostCommentDto(myProfileImageUrl, myNickname, commentContent);
                        adapter.addItem(comment);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<ResponseDto> call, Throwable t) {

                    }
                });

                edtCommentContent.clearFocus();
            }
        });

        setCommentListAdapter(dto.getCommentList());
    }

    private void setCommentListAdapter(List<PostCommentDto> commentList) {
        for(PostCommentDto item : commentList) {
            adapter.addItem(item);
        }
        adapter.notifyDataSetChanged();
    }

    private boolean checkAuthor(String authorEmail) {
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        String email = preferences.getString("user", "");
        if(StringUtils.isBlank(email) || !email.equals(authorEmail)) {
            return false;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();

        if (v == imgBtnMenu) {
            menuInflater.inflate(R.menu.post_menu, menu);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("정말로 삭제하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        retrofitService.postApi.deletePost(postId).enqueue(new Callback<ResponseDto>() {
                            @Override
                            public void onResponse(Call<ResponseDto> call, Response<ResponseDto> response) {
                                Toast.makeText(PostDetailActivity.this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ResponseDto> call, Throwable t) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                break;
            case R.id.menu_modify:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFocus() {
        if(edtCommentContent.requestFocus()) {
            ((InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(edtCommentContent, 0);
        }
    }

    private void hideKeyPad() {
        View view = getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}