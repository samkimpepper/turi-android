package com.example.turibuildtest0405.util.cookie;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {
    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> preferences = (HashSet<String>) context.getSharedPreferences("cookieData", Context.MODE_PRIVATE).getStringSet("cookie", null);

        System.out.println("여기는 AddCookie인터셉터임");

        if(preferences != null) {
            for(String cookie : preferences) {
                System.out.println("여기는 AddCookie인터셉터의 for문 안임");
                builder.addHeader("Cookie", cookie);
            }
        }

        return chain.proceed(builder.build());
    }
}
