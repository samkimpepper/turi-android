package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.turibuildtest0405.MainActivity;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.UserRequestDto;
import com.example.turibuildtest0405.dto.user.UserInfoDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.util.turiapi.RetrofitService;

public class LoginActivity extends AppCompatActivity {

    EditText lId, lPwd;
    Button lLogin, lRegister;

    RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofitService = RetrofitService.getInstance(getApplicationContext());

        lId = findViewById(R.id.ed_lId);
        lPwd = findViewById(R.id.ed_lPwd);

        lLogin = findViewById(R.id.btn_lLogin);
        lRegister = findViewById(R.id.btn_lRegister);

        SpannableString content = new SpannableString("sign up");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        lRegister.setText(content);

        lRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        lLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = lId.getText().toString();
                String password = lPwd.getText().toString();

                UserRequestDto.Login login = new UserRequestDto.Login();
                login.setEmail(email);
                login.setPassword(password);
                System.out.println(login.getEmail());

                retrofitService.userApi.login(login).enqueue(new Callback<ResponseDto.Data<UserInfoDto>>() {
                    @Override
                    public void onResponse(Call<ResponseDto.Data<UserInfoDto>> call, Response<ResponseDto.Data<UserInfoDto>> response) {
                        ResponseDto.Data<UserInfoDto> responseDto = response.body();
                        UserInfoDto userInfoDto;
                        if (response.isSuccessful() && responseDto != null) {
                            System.out.println(responseDto.getMessage());
                            Toast.makeText(LoginActivity.this,
                                    email + "님 환영합니다.",
                                    Toast.LENGTH_SHORT).show();

                            // SharedPreferences로 사용자 정보 저장
                            // 서버에서 로그인 성공할 때 이것저것 정보도 전달하도록 변경해야함
                            userInfoDto = responseDto.getData();
                            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", userInfoDto.getEmail());
                            editor.putString("nickname", userInfoDto.getNickname());
                            editor.putString("profileImageUrl", userInfoDto.getProfileImageUrl());
                            editor.commit();

                            // 로그인 성공 후 메인 액티로 이동
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseDto.Data<UserInfoDto>> call, Throwable t) {

                    }
                });
            }
        });
    }
}