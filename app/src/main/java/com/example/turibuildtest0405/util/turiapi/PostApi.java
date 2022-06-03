package com.example.turibuildtest0405.util.turiapi;

import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.post.PostDetailDto;
import com.example.turibuildtest0405.dto.post.PostRequestDto;
import com.example.turibuildtest0405.dto.post.PostSearchDto;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface PostApi {
    @Multipart
    @POST("post")
    Call<ResponseDto> create(@Part MultipartBody.Part profileImage, @PartMap HashMap<String, RequestBody> data);


    @GET("post/search")
    Call<ResponseDto.DataList<PostSearchDto>> search(@Path("keyword")String keyword);

    @GET("post")
    Call<ResponseDto.Data<PostDetailDto>> detailView(@Path("postId")Long postId);


}
