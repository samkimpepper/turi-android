package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.util.turiapi.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {
    RetrofitService retrofitService;

    ImageButton imgBtnBack;
    TextView tvPasswordChange, tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        retrofitService = RetrofitService.getInstance(getApplicationContext());

        tvLogout = findViewById(R.id.tvLogout);
        imgBtnBack = findViewById(R.id.SettingImgBtnBack);

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitService.userApi.logout().enqueue(new Callback<ResponseDto>() {
                    @Override
                    public void onResponse(Call<ResponseDto> call, Response<ResponseDto> response) {

                        Toast.makeText(getApplicationContext(), "로그아웃이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseDto> call, Throwable t) {

                    }
                });
            }
        });


    }
}