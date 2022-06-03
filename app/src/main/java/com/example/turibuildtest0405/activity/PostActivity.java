package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.kakaomap.MapSearchKeywordResult;
import com.example.turibuildtest0405.dto.post.PostRequestDto;
import com.example.turibuildtest0405.util.turiapi.DataService;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    private DataService dataService;
    private PostRequestDto.Create dto;

    private ImageButton imgBtn;
    private Button btnMoveSearch, btnSubmit;
    private EditText edtContent;

    HashMap<String, RequestBody> dataMap;

    private Uri selectedImage;
    private boolean isLocationSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        dataService = new DataService(getApplicationContext());

        dataMap = new HashMap<>();

        imgBtn = findViewById(R.id.imgBtn);
        btnMoveSearch = findViewById(R.id.btnMoveSearch);
        btnSubmit = findViewById(R.id.btnSubmit);
        edtContent = findViewById(R.id.edtContent);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 200);
            }
        });


        btnMoveSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostSearchActivity.class);
                startActivityForResult(intent, 201);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedImage == null) {
                    Toast.makeText(PostActivity.this, "사진을 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isLocationSelected) {
                    dataMap.put("content", RequestBody.create(MediaType.parse("text/plain"), edtContent.toString()));
                    dataMap.put("postType", RequestBody.create(MediaType.parse("text/plain"), "food"));
                }

                MultipartBody.Part uploadFile = forImageSend();

                dataService.postApi.create(uploadFile, dataMap).enqueue(new Callback<ResponseDto>() {
                    @Override
                    public void onResponse(Call<ResponseDto> call, Response<ResponseDto> response) {
                        ResponseDto responseDto = response.body();
                        Log.d("Post 만들기 응답", "onResponse: " + responseDto.getMessage());
                        Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();

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
            imgBtn.setImageURI(selectedImage);
        } else if(requestCode == 201) {
            MapSearchKeywordResult.Place place = (MapSearchKeywordResult.Place) data.getSerializableExtra("place");

            dataMap.put("placeName", RequestBody.create(MediaType.parse("text/plain"), place.getPlace_name()));
            dataMap.put("roadAddress", RequestBody.create(MediaType.parse("text/plain"), place.getRoad_address_name()));
            dataMap.put("jibunAddress", RequestBody.create(MediaType.parse("text/plain"), place.getAddress_name()));
            dataMap.put("x", RequestBody.create(MediaType.parse("text/plain"), place.getX()));
            dataMap.put("y", RequestBody.create(MediaType.parse("text/plain"), place.getY()));

            isLocationSelected = true;
        }
    }



    private MultipartBody.Part forImageSend() {

        Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        String mediaPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));

        // 사진 보낼 준비
        File file = new File(mediaPath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("profileImage", file.getName(), requestFile);

        cursor.close();

        return uploadFile;
    }

}