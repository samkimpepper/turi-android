package com.example.turibuildtest0405.util.kakaoapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KakaoMapService {
    private String BASE_URL =  "https://dapi.kakao.com/";
    public KakaoMapApi kakaoMapApi;

    public KakaoMapService() {
        Retrofit retrofitClient = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        kakaoMapApi = retrofitClient.create(KakaoMapApi.class);
    }
}

