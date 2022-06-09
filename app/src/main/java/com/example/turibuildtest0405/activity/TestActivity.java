package com.example.turibuildtest0405.activity;

import com.example.turibuildtest0405.MainActivity;
import com.example.turibuildtest0405.dto.post.PostDetailDto;
import com.example.turibuildtest0405.dto.user.UserInfoDto;
import com.example.turibuildtest0405.util.turiapi.DataService;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.User;
import com.example.turibuildtest0405.dto.UserRequestDto;
import com.example.turibuildtest0405.util.turiapi.RetrofitService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {
    DataService dataService;

    Button button, btnLogin, btnMoveDetail;

    PostDetailDto detailDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        dataService = new DataService(getApplicationContext());
        //getHashKey();

        button = findViewById(R.id.btn_test);
        btnLogin = findViewById(R.id.btn_login);
        btnMoveDetail = findViewById(R.id.btn_post_detail);

        btnMoveDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDetailApi(1L);

            }
        });

        String email = "hungry123@naver.com";
        String password="buttercookie";
        String nickname = "딸기쿠키";
        UserRequestDto.SignUp signup = new UserRequestDto.SignUp();
        signup.setEmail(email);
        signup.setNickname(nickname);
        signup.setPassword(password);
        signup(signup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataService.userApi.test().enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.out.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });


    }
    private void signup(UserRequestDto.SignUp signup) {
        dataService.userApi.signUp(signup).enqueue(new Callback<ResponseDto>() {
            @Override
            public void onResponse(Call<ResponseDto> call, Response<ResponseDto> response) {

                System.out.println(response.body());
                ResponseDto responseDto = response.body();
                if(responseDto != null) {
                    System.out.println(responseDto.getMessage());
                }

                Toast.makeText(TestActivity.this, "성공", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseDto> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void login() {
        UserRequestDto.Login login = new UserRequestDto.Login();
        login.setEmail("hungry123@naver.com");
        login.setPassword("buttercookie");
        dataService.userApi.login(login).enqueue(new Callback<ResponseDto.Data<UserInfoDto>>() {
            @Override
            public void onResponse(Call<ResponseDto.Data<UserInfoDto>> call, Response<ResponseDto.Data<UserInfoDto>> response) {
                Toast.makeText(getApplicationContext(), "로그인 성공.. 아마도", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseDto.Data<UserInfoDto>> call, Throwable t) {

            }
        });
    }

    private void callDetailApi(Long postId) {
        dataService.postApi.detailView(postId).enqueue(new Callback<ResponseDto.Data<PostDetailDto>>() {
            @Override
            public void onResponse(Call<ResponseDto.Data<PostDetailDto>> call, Response<ResponseDto.Data<PostDetailDto>> response) {
                detailDto = response.body().getData();
                Log.d("SamePlacePostActivity", "onResponse: " + detailDto.getContent());
                Intent intent = new Intent(TestActivity.this, PostDetailActivity.class);
                intent.putExtra("postDetailDto", detailDto);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseDto.Data<PostDetailDto>> call, Throwable t) {

            }
        });
    }

//
//    private void getHashKey(){
//        PackageInfo packageInfo = null;
//        try {
//            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (packageInfo == null)
//            Log.e("KeyHash", "KeyHash:null");
//
//        for (Signature signature : packageInfo.signatures) {
//            try {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            } catch (NoSuchAlgorithmException e) {
//                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
//            }
//        }
//    }
}