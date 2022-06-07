package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.util.turiapi.RetrofitService;

public class SettingActivity extends AppCompatActivity {
    RetrofitService retrofitService;

    TextView tvPasswordChange, tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        retrofitService = RetrofitService.getInstance(getApplicationContext());

        tvLogout = findViewById(R.id.tvLogout);

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}