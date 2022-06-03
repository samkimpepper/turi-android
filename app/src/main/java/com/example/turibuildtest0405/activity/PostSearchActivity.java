package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.turibuildtest0405.activity.adapter.DetailClickCallbackListener;
import com.example.turibuildtest0405.activity.adapter.PostSearchResultAdapter;
import com.example.turibuildtest0405.util.kakaoapi.KakaoMapService;
import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.dto.kakaomap.MapSearchKeywordResult;
import com.example.turibuildtest0405.dto.kakaomap.MapSearchKeywordResult.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import net.daum.mf.map.api.MapView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostSearchActivity extends AppCompatActivity  {
    KakaoMapService kakaoMapService;

    EditText edtSearch;
    Button btnSearch, btnChoose;
    TextView tvResult;
    ListView listView;
    PostSearchResultAdapter adapter;
    DetailClickCallbackListener callbackListener = new DetailClickCallbackListener() {
        @Override
        public void callback(Place place) {
            Intent intent = new Intent();
            intent.putExtra("place", place);
            setResult(201, intent);
            finish();
        }
    };


    private final String API_KEY = "KakaoAK 323c2e0d7294b4537355f73e4b88e9cc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        kakaoMapService = new KakaoMapService();

        adapter = new PostSearchResultAdapter(callbackListener);

        btnSearch = findViewById(R.id.btnSearch);
        edtSearch = findViewById(R.id.edtSearch);
        listView = (ListView) findViewById(R.id.listViewSearch);

        listView.setAdapter(adapter);

        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.mapView);
        mapViewContainer.addView(mapView);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = edtSearch.getText().toString();
                executeKeywordSearch(query);
            }
        });
    }

    public void executeKeywordSearch(String keyword) {
        kakaoMapService.kakaoMapApi.getSearchResult(API_KEY, keyword).enqueue(new Callback<MapSearchKeywordResult>() {
            @Override
            public void onResponse(Call<MapSearchKeywordResult> call, Response<MapSearchKeywordResult> response) {
                MapSearchKeywordResult result = response.body();
                int cnt = result.getMeta().getTotal_count();
                Log.d("카카오맵검색", "onResponse: "+cnt);
                List<Place> places = result.getDocuments();
                for(int i=0; i<places.size(); i++) {
                    adapter.addItem(places.get(i));
                    Log.d("검색 결과", "onResponse: "+ places.get(i).getPlace_name());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MapSearchKeywordResult> call, Throwable t) {

            }
        });
    }


}