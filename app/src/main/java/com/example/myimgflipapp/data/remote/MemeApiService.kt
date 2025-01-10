package com.example.myimgflipapp.data.remote

import com.example.myimgflipapp.data.remote.dto.MemeResponse
import retrofit2.http.GET


interface MemeApiService {
    @GET("get_memes")
    suspend fun fetchMemes(): MemeResponse
}