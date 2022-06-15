package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.activity.adapter.ImageDetailClickCallbackListener;
import com.example.turibuildtest0405.activity.adapter.ImageGridAdapter;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.post.MyPostDto;
import com.example.turibuildtest0405.dto.post.PostDetailDto;
import com.example.turibuildtest0405.util.CommonUtil;
import com.example.turibuildtest0405.util.turiapi.RetrofitService;

import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    RetrofitService retrofitService;

    ImageView ivMovePost, ivMoveUserInfo;

    ImageView ivProfile;
    TextView tvNickname;

    GridView gridView;
    ImageGridAdapter adapter;
    PostDetailDto detailDto;
    ImageDetailClickCallbackListener callbackListener = new ImageDetailClickCallbackListener() {
        @Override
        public void callback(MyPostDto post) {
            callDetailApi(post.getPostId());
            if(detailDto == null) {
                return;
            }

            Intent intent = new Intent(UserActivity.this, PostDetailActivity.class);
            intent.putExtra("postDetailDto", detailDto);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        retrofitService = RetrofitService.getInstance(getApplicationContext());

        ivMovePost = findViewById(R.id.ivNewPost);
        ivMoveUserInfo = findViewById(R.id.ivProfileEdit);
        ivProfile = findViewById(R.id.userivProfile);
        tvNickname = findViewById(R.id.usertvNickname);

        // SharePreferences에서 프사, 닉네임 가져오기
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        tvNickname.setText(preferences.getString("nickname", "실패"));
        String profileImageUrl = preferences.getString("profileImage", "");
        if(StringUtils.isNotEmpty(profileImageUrl)) {
            //ivProfile.setImageBitmap(CommonUtil.resizeImage(getApplicationContext(), profileImageUrl, 60));
            Glide.with(ivProfile).load(preferences.getString("profileImage", "실패"));

        }

        gridView = findViewById(R.id.gridView);
        adapter = new ImageGridAdapter(callbackListener);
        gridView.setAdapter(adapter);
        new Thread() {
            public void run() {
                try {
                    callMyPostApi();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();

        ivMovePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });

        ivMoveUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MyInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void callMyPostApi() {
        retrofitService.postApi.getMyPostList().enqueue(new Callback<ResponseDto.DataList<MyPostDto>>() {
            @Override
            public void onResponse(Call<ResponseDto.DataList<MyPostDto>> call, Response<ResponseDto.DataList<MyPostDto>> response) {
                assert response.body() != null;
                List<MyPostDto> postList = response.body().getData();
                if(postList.size() == 0) {
                    return;
                }

                for(MyPostDto post : postList) {
                    adapter.addItem(post);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseDto.DataList<MyPostDto>> call, Throwable t) {

            }
        });
    }

    private void callDetailApi(Long postId) {

        retrofitService.postApi.detailView(postId).enqueue(new Callback<ResponseDto.Data<PostDetailDto>>() {
            @Override
            public void onResponse(Call<ResponseDto.Data<PostDetailDto>> call, Response<ResponseDto.Data<PostDetailDto>> response) {
                detailDto = response.body().getData();
                if(response.body().getState() != 200) {
                    return;
                }
                Log.d("UserActivity", "onResponse: " + response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ResponseDto.Data<PostDetailDto>> call, Throwable t) {

            }
        });
    }
}