package com.example.turibuildtest0405.activity;

import com.example.turibuildtest0405.MainActivity;
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

    Button button, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        dataService = new DataService(getApplicationContext());


        //getHashKey();

        button = findViewById(R.id.btn_test);
        btnLogin = findViewById(R.id.btn_login);

        String email = "hungry123@naver.com";
        String password="buttercookie";
        String nickname = "딸기쿠키";
        UserRequestDto.SignUp signup = new UserRequestDto.SignUp();
        signup.setEmail(email);
        signup.setNickname(nickname);
        signup.setPassword(password);
        signup(signup);

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