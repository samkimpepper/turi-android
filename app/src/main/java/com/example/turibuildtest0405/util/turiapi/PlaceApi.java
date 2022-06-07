package com.example.turibuildtest0405.util.turiapi;

import com.example.turibuildtest0405.dto.PlaceDto;
import com.example.turibuildtest0405.dto.ResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceApi {
    @GET("place/near")
    Call<ResponseDto.DataList<PlaceDto>> getNearPlaces(@Query("x") String x, @Query("y") String y, @Query("type") String type);

}
