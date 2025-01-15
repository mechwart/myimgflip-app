package com.example.myimgflipapp.data.remote.dto

data class GetMemeDto(
    val id: String,
    val name: String,
    val url: String,
    val width: Int,
    val height: Int,
    val box_count: Int
)