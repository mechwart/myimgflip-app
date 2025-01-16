package com.example.myimgflipapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemeDao {
    @Insert
    suspend fun insertMeme(meme: Meme)

    @Query("SELECT * FROM memes WHERE username = :username ORDER BY createdAt DESC LIMIT 1")
    fun getLastMemeForUser(username: String): LiveData<Meme>
}