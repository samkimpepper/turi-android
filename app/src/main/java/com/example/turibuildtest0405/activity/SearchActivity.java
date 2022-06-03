package com.example.turibuildtest0405.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.turibuildtest0405.R;
import com.example.turibuildtest0405.activity.adapter.DetailClickCallbackListener2;
import com.example.turibuildtest0405.activity.adapter.SearchResultAdapter;
import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.post.PostDetailDto;
import com.example.turibuildtest0405.dto.post.PostSearchDto;
import com.example.turibuildtest0405.util.turiapi.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch;
    ListView listView;

    PostDetailDto postDetailDto;

    RetrofitService retrofitService;

    SearchResultAdapter adapter;
    DetailClickCallbackListener2 callbackListener = new DetailClickCallbackListener2() {
        @Override
        public void callback(PostSearchDto dto) {
            callDetailApi(dto.getPostId());
            if(postDetailDto == null) {
                return;
            }

            Intent intent = new Intent(getApplicationContext(), PostDetailActivity.class);
            intent.putExtra("postDetailDto", postDetailDto);
            startActivity(intent);

        }
    };

    private void callDetailApi(Long postId) {

        retrofitService.postApi.detailView(postId).enqueue(new Callback<ResponseDto.Data<PostDetailDto>>() {
            @Override
            public void onResponse(Call<ResponseDto.Data<PostDetailDto>> call, Response<ResponseDto.Data<PostDetailDto>> response) {
                ResponseDto.Data<PostDetailDto> responseDto = response.body();
                postDetailDto = responseDto.getData();
            }

            @Override
            public void onFailure(Call<ResponseDto.Data<PostDetailDto>> call, Throwable t) {

            }
        });
    }

//    private void nogada() {
////        PostSearchDto dto = new PostSearchDto("내용", "food", "연희로 10길", "지번주소", "연희빵집", 13.12f, 15.123f);
////        PostSearchDto dto2 = new PostSearchDto("내용2", "food", "희우정로 3길", "지번주소2", "오린지", 12.142f, 15.341f);
////
////        List<PostSearchDto> list = new ArrayList<>();
////        list.add(dto);
////        list.add(dto2);
//
//        searchListDto.setState(200);
//        searchListDto.setMessage("가짜 성공");
//        searchListDto.setData(list);
//        searchListDto.setTotalCount(list.size());
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        retrofitService = RetrofitService.getInstance(getApplicationContext());

        adapter = new SearchResultAdapter(callbackListener);

        edtSearch = findViewById(R.id.edtSearchMain);
        btnSearch = findViewById(R.id.btnSearchMain);
        listView = findViewById(R.id.SlistView);

        listView.setAdapter(adapter);

        //nogada();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = edtSearch.getText().toString();
                // 여기서 api 호출해서 결과 리스트로 받아왔다고 치고
                retrofitService.postApi.search(keyword).enqueue(new Callback<ResponseDto.DataList<PostSearchDto>>() {
                    @Override
                    public void onResponse(Call<ResponseDto.DataList<PostSearchDto>> call, Response<ResponseDto.DataList<PostSearchDto>> response) {
                        ResponseDto.DataList<PostSearchDto> responseDto = response.body();
                        Log.d("SearchActivity", "onResponse: " + responseDto.getMessage());
                        Toast.makeText(getApplicationContext(), responseDto.getMessage(), Toast.LENGTH_SHORT).show();

                        // 여기서부터 데이터 리스트 어댑터로 붙임
                        List<PostSearchDto> list = responseDto.getData();
                        for(PostSearchDto item : list) {
                            adapter.addItem(item);
                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<ResponseDto.DataList<PostSearchDto>> call, Throwable t) {

                    }
                });

            }
        });

    }
}