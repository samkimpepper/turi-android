package com.example.turibuildtest0405.util.cookie;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        System.out.println("여기는 RecvCookie인터셉터임");

        if(!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for(String header : originalResponse.headers("Set-Cookie")) {
                System.out.println("쿠키 집어넣는 중" + header);
                cookies.add(header);
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences("cookieData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("cookie", cookies);
            editor.commit();
        }

        return originalResponse;
    }
}
