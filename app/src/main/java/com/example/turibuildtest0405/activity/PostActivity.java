package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.kakaomap.MapSearchKeywordResult;
import com.example.turibuildtest0405.dto.post.PostRequestDto;
import com.example.turibuildtest0405.util.CommonUtil;
import com.example.turibuildtest0405.util.turiapi.DataService;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

    private ImageView imgBtn;
    Button btnMoveSearch, btnSubmit;
    private EditText edtContent;
    TextView tvLocationInfo;
    Spinner spinner;
    String postType = null;

    HashMap<String, RequestBody> dataMap;

    private Uri selectedImage;
    private boolean isLocationSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        dataService = new DataService(getApplicationContext());

        dataMap = new HashMap<>();

        imgBtn = findViewById(R.id.imgBtnGallery);
        btnMoveSearch = findViewById(R.id.btnMoveSearch);
        btnSubmit = findViewById(R.id.PostbtnSubmit);
        edtContent = findViewById(R.id.edtContent);
        tvLocationInfo = findViewById(R.id.tvLocationInfo);

        setSpinner();

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

                if(postType == null) {
                    Toast.makeText(PostActivity.this, "게시글 카테고리를 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isLocationSelected) {
                    Toast.makeText(PostActivity.this, "지도에서 장소를 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                dataMap.put("content", RequestBody.create(MediaType.parse("text/plain"), edtContent.getText().toString()));
                dataMap.put("postType", RequestBody.create(MediaType.parse("text/plain"), postType));
                dataMap.put("rating", RequestBody.create(MediaType.parse("text/plain"), "3"));

                MultipartBody.Part uploadFile = CommonUtil.forImageSend2(getContentResolver(), selectedImage);

                dataService.postApi.create(dataMap, uploadFile).enqueue(new Callback<ResponseDto>() {
                    @Override
                    public void onResponse(Call<ResponseDto> call, Response<ResponseDto> response) {
                        ResponseDto responseDto = response.body();
                        Log.d("Post 만들기 응답", "onResponse: " + responseDto.getMessage());
                        Toast.makeText(getApplicationContext(), "게시글 작성 완료", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseDto> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void setSpinner() {
        spinner = findViewById(R.id.spinnerList);

        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();

                if(value.equals("맛집")) {
                    postType = "food";
                } else if(value.equals("숙소")) {
                    postType = "stay";
                } else if(value.equals("놀거리")) {
                    postType = "enjoy";
                }

                Log.d("Spinner Click", "onItemClick: " + postType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

            tvLocationInfo.setText(place.getPlace_name() + ", " + place.getRoad_address_name());

            isLocationSelected = true;
        }
    }
    //filepath는 String 변수로 갤러리에서 이미지를 가져올 때 photoUri.getPath()를 통해 받아온다. File file = new File(filepath); InputStream inputStream = null; try { inputStream = getContext().getContentResolver().openInputStream(photoUri); }catch(IOException e) { e.printStackTrace(); } Bitmap bitmap = BitmapFactory.decodeStream(inputStream); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream); RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray()); MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("postImg", file.getName() ,requestBody);

//    private void push() {
//        //filepath는 String 변수로 갤러리에서 이미지를 가져올 때 photoUri.getPath()를 통해 받아온다.
//        File file = new File(filepath);
//        InputStream inputStream = null;
//        try {
//            inputStream = getContext().getContentResolver().openInputStream(photoUri);
//        }catch(IOException e) {
//            e.printStackTrace();
//        }
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray());
//        MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("postImg", file.getName() ,requestBody);
//    }

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