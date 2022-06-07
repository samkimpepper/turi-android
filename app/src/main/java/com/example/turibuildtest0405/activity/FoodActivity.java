package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.activity.adapter.TypeDetailClickCallbackListener;
import com.example.turibuildtest0405.activity.adapter.TypeListAdapter;
import com.example.turibuildtest0405.dto.PlaceDto;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.post.PostSearchDto;
import com.example.turibuildtest0405.util.turiapi.RetrofitService;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import net.daum.mf.map.api.MapView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity {
    RetrofitService retrofitService;
    double x, y;
    List<PlaceDto> placeDtoList;
    MapView mMapView;

    SlidingUpPanelLayout layout;
    ListView listView;
    TypeListAdapter adapter;

    TypeDetailClickCallbackListener callbackListener = new TypeDetailClickCallbackListener() {
        @Override
        public void callback(PlaceDto place) {
            retrofitService.postApi.getSamePlacePost(place.getPlaceId()).enqueue(new Callback<ResponseDto.DataList<PostSearchDto>>() {
                @Override
                public void onResponse(Call<ResponseDto.DataList<PostSearchDto>> call, Response<ResponseDto.DataList<PostSearchDto>> response) {

                }

                @Override
                public void onFailure(Call<ResponseDto.DataList<PostSearchDto>> call, Throwable t) {

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);


    }
}