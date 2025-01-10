package com.example.myimgflipapp.data.repository

import com.example.myimgflipapp.data.remote.MemeApiService
import com.example.myimgflipapp.data.remote.dto.MemeDto
import javax.inject.Inject


class MemeRepository @Inject constructor(private val memeService: MemeApiService) {

    suspend fun getMemes(): List<MemeDto> {
        val memeResponse = memeService.fetchMemes()

        return memeResponse.data.memes
    }
}