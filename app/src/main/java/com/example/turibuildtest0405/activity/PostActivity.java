package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.kakaomap.MapSearchKeywordResult;
import com.example.turibuildtest0405.dto.post.PostRequestDto;
import com.example.turibuildtest0405.util.CommonUtil;
import com.example.turibuildtest0405.util.turiapi.DataService;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
                hideKeyPad();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 200);
            }
        });

        edtContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideKeyPad();
                        }
                    }, 100);
                }
            }
        });


        btnMoveSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyPad();
                Intent intent = new Intent(getApplicationContext(), PostSearchActivity.class);
                startActivityForResult(intent, 201);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyPad();

                if(selectedImage == null) {
                    Toast.makeText(PostActivity.this, "????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(postType == null) {
                    Toast.makeText(PostActivity.this, "????????? ??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isLocationSelected) {
                    Toast.makeText(PostActivity.this, "???????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
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
                        Log.d("Post ????????? ??????", "onResponse: " + responseDto.getMessage());
                        Toast.makeText(getApplicationContext(), "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
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
                hideKeyPad();
                String value = adapterView.getItemAtPosition(i).toString();

                if(value.equals("??????")) {
                    postType = "food";
                } else if(value.equals("??????")) {
                    postType = "stay";
                } else if(value.equals("?????????")) {
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
        hideKeyPad();
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

    private void hideKeyPad() {
        View view = getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //filepath??? String ????????? ??????????????? ???????????? ????????? ??? photoUri.getPath()??? ?????? ????????????. File file = new File(filepath); InputStream inputStream = null; try { inputStream = getContext().getContentResolver().openInputStream(photoUri); }catch(IOException e) { e.printStackTrace(); } Bitmap bitmap = BitmapFactory.decodeStream(inputStream); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream); RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray()); MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("postImg", file.getName() ,requestBody);

//    private void push() {
//        //filepath??? String ????????? ??????????????? ???????????? ????????? ??? photoUri.getPath()??? ?????? ????????????.
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

        // ?????? ?????? ??????
        File file = new File(mediaPath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("profileImage", file.getName(), requestFile);

        cursor.close();

        return uploadFile;
    }

}