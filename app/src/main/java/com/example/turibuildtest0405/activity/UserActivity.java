package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.turibuildtest0405.R;

public class UserActivity extends AppCompatActivity {
    ImageView ivMovePost, ivMoveUserInfo;

    ImageView ivProfile;
    TextView tvNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ivMovePost = findViewById(R.id.ivNewPost);
        ivMoveUserInfo = findViewById(R.id.ivProfileEdit);
        ivProfile = findViewById(R.id.userivProfile);
        tvNickname = findViewById(R.id.usertvNickname);

        // SharePreferences에서 프사, 닉네임 가져오기
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        tvNickname.setText(preferences.getString("nickname", "실패"));
        Glide.with(ivProfile).load(preferences.getString("profileImage", "실패"));

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
}