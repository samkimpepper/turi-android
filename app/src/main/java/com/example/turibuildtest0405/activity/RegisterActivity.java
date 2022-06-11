package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.UserRequestDto;
import com.example.turibuildtest0405.util.turiapi.DataService;

public class RegisterActivity extends AppCompatActivity {

    private EditText rId, rPwd, rName;
    private Button rRegister;
    private TextView btnMoveLogin;
    private AlertDialog dialog;
    private boolean validate = false;

    DataService dtService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Intent intent = new Intent(RegisterActivity.this, TestActivity.class);
//        startActivity(intent);

        dtService = new DataService(getApplicationContext());

        rId = findViewById(R.id.ed_rId);
        rPwd = findViewById(R.id.ed_rPwd);
        rName = findViewById(R.id.ed_rName);

        rRegister = findViewById(R.id.btn_rRegister);

        rRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = rId.getText().toString().trim();
                String password = rPwd.getText().toString().trim();
                String nickname = rName.getText().toString().trim();

                Pattern emailPattern = Patterns.EMAIL_ADDRESS;

                if(email.equals("")){   //이메일이 공백인지 체크
                    System.out.println("이메일이 공백임");
                    Toast.makeText(RegisterActivity.this,
                            "이메일을 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(password.equals("")){   //비밀번호가 공백인지 체크
                    System.out.println("비밀번호가 공백임");
                    Toast.makeText(RegisterActivity.this,
                            "비밀번호를 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(nickname.equals("")){   //이름이 공백인지 체크
                    System.out.println("닉네임이 공백임");
                    Toast.makeText(RegisterActivity.this,
                            "닉네임을 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(emailPattern.matcher(email).matches() == false){ //아이디가 이메일 형식인지 체크
                    System.out.println("아이디가 이메일이 아님");
                    Toast.makeText(RegisterActivity.this,
                            "아이디가 이메일 형식인지 확인해주세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                UserRequestDto.SignUp signup = new UserRequestDto.SignUp();
                signup.setEmail(email);
                signup.setNickname(nickname);
                signup.setPassword(password);

                dtService.userApi.signUp(signup).enqueue(new Callback<ResponseDto>() {
                    @Override
                    public void onResponse(Call<ResponseDto> call, Response<ResponseDto> response) {

                        System.out.println(response.body());
                        ResponseDto responseDto = response.body();

                        if(responseDto.getMessage().equals("이미 존재하는 이메일")){ //이메일 중복시 return
                            Toast.makeText(RegisterActivity.this,
                                    "중복된 이메일입니다.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(responseDto.getMessage() != null) {  //회원가입 성공
                            System.out.println(responseDto.getMessage());
                        }


                        Toast.makeText(RegisterActivity.this,
                                "성공",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<ResponseDto> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        btnMoveLogin = findViewById(R.id.tvMoveLogin);
        btnMoveLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}