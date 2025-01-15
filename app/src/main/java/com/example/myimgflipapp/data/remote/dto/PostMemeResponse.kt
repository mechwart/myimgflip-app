package com.example.myimgflipapp.data.remote.dto

data class PostMemeResponse(
    val success: Boolean,
    val data: PostMemeDto,
    val error_message: String
)
