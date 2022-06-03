package com.example.turibuildtest0405.util.turiapi;

import android.content.Context;

import com.example.turibuildtest0405.util.cookie.AddCookiesInterceptor;
import com.example.turibuildtest0405.util.cookie.ReceivedCookiesInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataService {
    private Retrofit retrofitClient;
    private String BASE_URL = "http://3.88.102.16:8080/";
    public UserApi userApi;
    public PostApi postApi;


    public DataService(Context context) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .addInterceptor(new AddCookiesInterceptor(context))
                .build();

        retrofitClient =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        userApi = retrofitClient.create(UserApi.class);
        postApi = retrofitClient.create(PostApi.class);
    }


}