package com.example.turibuildtest0405.util.turiapi;

import com.example.turibuildtest0405.dto.ResponseDto;
import com.example.turibuildtest0405.dto.post.MyPostDto;
import com.example.turibuildtest0405.dto.post.PostDetailDto;
import com.example.turibuildtest0405.dto.post.PostRequestDto;
import com.example.turibuildtest0405.dto.post.PostSearchDto;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface PostApi {
    @Multipart
    @POST("post/create2")
    Call<ResponseDto> create(@PartMap HashMap<String, RequestBody> data, @Part MultipartBody.Part file);

    @GET("post/search/{keyword}")
    Call<ResponseDto.DataList<PostSearchDto>> search(@Path("keyword")String keyword);

    @GET("post/{postId}")
    Call<ResponseDto.Data<PostDetailDto>> detailView(@Path("postId")Long postId);

    @GET("post/place/{placeId}")
    Call<ResponseDto.DataList<PostSearchDto>> getSamePlacePost(@Path("placeId") Long placeId);

    @DELETE("post/{postId}")
    Call<ResponseDto> deletePost(@Path("postId") Long postId);

    @GET("post/my")
    Call<ResponseDto.DataList<MyPostDto>> getMyPostList();
}
