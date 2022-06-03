package com.example.turibuildtest0405.util.kakaoapi;

import com.example.turibuildtest0405.dto.kakaomap.MapSearchKeywordResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoMapApi {
    @GET("v2/local/search/keyword.json")
    Call<MapSearchKeywordResult> getSearchResult(@Header("Authorization") String key, @Query("query") String query);

}