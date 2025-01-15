package com.example.myimgflipapp.data.remote

import com.example.myimgflipapp.data.remote.dto.GetMemesResponse
import com.example.myimgflipapp.data.remote.dto.PostMemeResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface MemeApiService {
    @GET("get_memes")
    suspend fun fetchMemes(): GetMemesResponse

    @FormUrlEncoded
    @POST("caption_image")
    suspend fun createMeme(
        @Field("template_id") templateId: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("text0") topText: String,
        @Field("text1") bottomText: String
    ): PostMemeResponse
}