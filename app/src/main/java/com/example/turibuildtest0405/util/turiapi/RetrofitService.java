package com.example.turibuildtest0405.util.turiapi;

import android.content.Context;

import com.example.turibuildtest0405.util.cookie.AddCookiesInterceptor;
import com.example.turibuildtest0405.util.cookie.ReceivedCookiesInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
* 싱글톤
* */
public class RetrofitService {
    private static Retrofit retrofitClient;
    private static String BASE_URL = "http://3.88.102.16:8080/";
    public UserApi userApi;
    public PostApi postApi;
    private static RetrofitService instance = null;

    private RetrofitService(Context context) {
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

    public static RetrofitService getInstance(Context context) {
        if(instance == null) {
            instance = new RetrofitService(context);
        }
        return instance;
    }

}
