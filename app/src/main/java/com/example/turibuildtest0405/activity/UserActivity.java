package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.turibuildtest0405.R;

public class UserActivity extends AppCompatActivity {
    ImageView ivMovePost, ivMoveUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ivMovePost = findViewById(R.id.ivNewPost);
        ivMoveUserInfo = findViewById(R.id.ivProfileEdit);

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
                Intent intent = new Intent(UserActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}