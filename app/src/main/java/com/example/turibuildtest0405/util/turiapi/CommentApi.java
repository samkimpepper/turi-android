package com.example.turibuildtest0405.util.turiapi;

import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.comment.CommentRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;

public interface CommentApi {

    @POST("comment")
    Call<ResponseDto> create(@Body CommentRequestDto commentRequestDto);


}
