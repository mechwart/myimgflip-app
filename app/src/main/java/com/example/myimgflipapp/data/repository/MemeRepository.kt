package com.example.myimgflipapp.data.repository

import androidx.lifecycle.LiveData
import com.example.myimgflipapp.data.db.Meme
import com.example.myimgflipapp.data.db.MemeDao
import com.example.myimgflipapp.data.remote.MemeApiService
import com.example.myimgflipapp.data.remote.dto.GetMemeDto
import javax.inject.Inject


class MemeRepository @Inject constructor(private val memeDao: MemeDao,
                                         private val memeService: MemeApiService) {

    suspend fun getMemes(): List<GetMemeDto> {
        val memeResponse = memeService.fetchMemes()

        return memeResponse.data.memes
    }

    suspend fun createAndSaveMeme(
        templateId: String,
        username: String,
        password: String,
        topText: String,
        bottomText: String
    ): Result<Meme> {
        return try {
            val response = memeService.createMeme(templateId, username, password, topText, bottomText)
            if (response.success) {
                val meme = Meme(
                    imageUrl = response.data.url,
                    username = username
                )
                memeDao.insertMeme(meme)

                Result.success(meme)
            } else {
                Result.failure(Exception(response.error_message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getLastMemeForUser(username: String): LiveData<Meme> {
        return memeDao.getLastMemeForUser(username)
    }
}