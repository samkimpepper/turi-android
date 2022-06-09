package com.example.turibuildtest0405.util.turiapi;

import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.UserRequestDto;
import com.example.turibuildtest0405.dto.user.UserInfoDto;
import com.example.turibuildtest0405.dto.user.UserPasswordDto;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface UserApi {
    @POST("user/signup")
    Call<ResponseDto> signUp(@Body UserRequestDto.SignUp signup);

    @POST("user/login")
    Call<ResponseDto.Data<UserInfoDto>> login(@Body UserRequestDto.Login login);

    @Multipart
    @PUT("user/update-info")
    Call<ResponseDto> updateUserInfo(@Part MultipartBody.Part file, @PartMap HashMap<String, RequestBody> data);

    @PUT("user/update-password")
    Call<ResponseDto> updatePassword(@Body UserPasswordDto dto);

    @GET("user/logout")
    Call<ResponseDto> logout();

    @GET("user/session-test")
    Call<String> test();
}