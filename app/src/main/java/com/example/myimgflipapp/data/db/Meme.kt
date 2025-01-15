package com.example.myimgflipapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memes")
data class Meme(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUrl: String,
    val username: String,
    val createdAt: Long = System.currentTimeMillis()
)
