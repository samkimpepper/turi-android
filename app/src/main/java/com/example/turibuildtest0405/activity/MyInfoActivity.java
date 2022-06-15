package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.util.CommonUtil;
import com.example.turibuildtest0405.util.turiapi.RetrofitService;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInfoActivity extends AppCompatActivity {
    EditText edtNickname;
    Button btnSelectImage, btnSubmit;
    ImageView ivProfileImage;

    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        RetrofitService retrofitService = RetrofitService.getInstance(getApplicationContext());

        edtNickname = findViewById(R.id.edtChangeNickname);
        ivProfileImage = findViewById(R.id.userivProfile2);
        btnSubmit = findViewById(R.id.UbtnSubmit);

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        edtNickname.setText(preferences.getString("nickname", ""));
        String profileImageUrl = preferences.getString("profileImage", "");
        if(StringUtils.isNotEmpty(profileImageUrl)) {
            //ivProfileImage.setImageBitmap(CommonUtil.resizeImage(getApplicationContext(), profileImageUrl, 150));
            Glide.with(getApplicationContext()).load(profileImageUrl).into(ivProfileImage);
        }

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 200);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = edtNickname.getText().toString();
                RequestBody nicknameParsed = RequestBody.create(MediaType.parse("text/plain"), nickname);
                HashMap<String, RequestBody> data = new HashMap<>();
                data.put("nickname", nicknameParsed);
                MultipartBody.Part uploadImage = CommonUtil.forImageSend2(getContentResolver(), selectedImage);

                retrofitService.userApi.updateUserInfo(uploadImage, data).enqueue(new Callback<ResponseDto>() {
                    @Override
                    public void onResponse(Call<ResponseDto> call, Response<ResponseDto> response) {
                        ResponseDto responseDto = response.body();
                        Log.d("닉넴 프사 바꾸기 응답임", responseDto.getMessage());
                        Toast.makeText(getApplicationContext(), responseDto.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseDto> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImage = data.getData();
            ivProfileImage.setImageURI(selectedImage);
        }
    }
}