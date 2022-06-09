package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.activity.adapter.DetailClickCallbackListener2;
import com.example.turibuildtest0405.activity.adapter.SamePlacePostListAdapter;
import com.example.turibuildtest0405.dto.PlaceDto;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.post.PostDetailDto;
import com.example.turibuildtest0405.dto.post.PostSearchDto;
import com.example.turibuildtest0405.util.turiapi.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SamePlacePostActivity extends AppCompatActivity {
    RetrofitService retrofitService;

    TextView tvPlaceName, tvAddress;
    ImageView ivPlace;

    ListView listView;
    SamePlacePostListAdapter adapter;

    ArrayList<PostSearchDto> postList;
    PlaceDto placeDto;
    PostDetailDto detailDto;

    DetailClickCallbackListener2 callbackListener = new DetailClickCallbackListener2() {
        @Override
        public void callback(PostSearchDto dto) {
            callDetailApi(dto.getPostId());
            if(detailDto == null) {
                return;
            }

            Intent intent = new Intent(SamePlacePostActivity.this, PostDetailActivity.class);
            intent.putExtra("postDetailDto", detailDto);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_place_post);

        retrofitService = RetrofitService.getInstance(getApplicationContext());

        postList = (ArrayList<PostSearchDto>) getIntent().getSerializableExtra("PostSearchDto");
        placeDto = (PlaceDto) getIntent().getSerializableExtra("PlaceDto");

        listView = findViewById(R.id.SamelistView);
        adapter = new SamePlacePostListAdapter(callbackListener);

        tvPlaceName = findViewById(R.id.SametvPlaceeName);
        tvAddress = findViewById(R.id.SametvAddress);
        tvPlaceName.setText(placeDto.getPlaceName());
        tvAddress.setText(placeDto.getRoadAddress());

        listView.setAdapter(adapter);

        for(PostSearchDto dto : postList) {
            adapter.addItem(dto);
        }
        adapter.notifyDataSetChanged();
    }

    private void callDetailApi(Long postId) {

        retrofitService.postApi.detailView(postId).enqueue(new Callback<ResponseDto.Data<PostDetailDto>>() {
            @Override
            public void onResponse(Call<ResponseDto.Data<PostDetailDto>> call, Response<ResponseDto.Data<PostDetailDto>> response) {
                detailDto = response.body().getData();
                Log.d("SamePlacePostActivity", "onResponse: " + response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ResponseDto.Data<PostDetailDto>> call, Throwable t) {

            }
        });
    }
}