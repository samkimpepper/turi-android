package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.activity.adapter.CommentListAdapter;
import com.example.turibuildtest0405.dto.comment.PostCommentDto;
import com.example.turibuildtest0405.dto.post.PostDetailDto;

import java.io.Serializable;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {
    ImageView ivProfileImage, ivPostImage;
    TextView tvNickname, tvContent;
    ListView listView;

    CommentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        ivPostImage = findViewById(R.id.ivPostImage);
        tvNickname = findViewById(R.id.tvNickname);
        tvContent = findViewById(R.id.DtvContent);
        listView = findViewById(R.id.DlistView);

        adapter = new CommentListAdapter();

        listView.setAdapter(adapter);

        Intent intent = getIntent();
        PostDetailDto dto = (PostDetailDto) intent.getSerializableExtra("postDetailDto");

        //Glide.with(this).load(dto.get)
        tvNickname.setText(dto.getNickname());
        tvContent.setText(dto.getContent());

        setCommentListAdapter(dto.getCommentList());
    }

    private void setCommentListAdapter(List<PostCommentDto> commentList) {
        for(PostCommentDto item : commentList) {
            adapter.addItem(item);
        }
        adapter.notifyDataSetChanged();
    }
}