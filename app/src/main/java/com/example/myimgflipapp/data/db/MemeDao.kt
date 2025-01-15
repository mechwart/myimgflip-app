package com.example.myimgflipapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemeDao {
    @Insert
    suspend fun insertMeme(meme: Meme)

    @Query("SELECT * FROM memes WHERE id = :id")
    fun getMemesById(id: Int): Meme?

    @Query("SELECT * FROM memes")
    fun getAllMemes(): List<Meme>
}