package com.example.myimgflipapp.dataTest.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.myimgflipapp.data.db.Meme
import com.example.myimgflipapp.data.db.MemeDao
import com.example.myimgflipapp.data.remote.MemeApiService
import com.example.myimgflipapp.data.remote.dto.GetMemeDto
import com.example.myimgflipapp.data.remote.dto.GetMemeListDto
import com.example.myimgflipapp.data.remote.dto.GetMemesResponse
import com.example.myimgflipapp.data.remote.dto.PostMemeDto
import com.example.myimgflipapp.data.remote.dto.PostMemeResponse
import com.example.myimgflipapp.data.repository.MemeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue

class MemeRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val memeDao: MemeDao = mockk(relaxed = true)
    private val memeService: MemeApiService = mockk()
    private val memeRepository = MemeRepository(memeDao, memeService)

    @Test
    fun `getMemes should return list of memes`() = runBlocking {
        // Given
        val memeList = GetMemeListDto(
            memes = listOf(
                GetMemeDto(
                    id = "1",
                    name = "Meme 1",
                    url = "https://www.example.com/meme1.jpg",
                    width = 1,
                    height = 1,
                    box_count = 1
                ),
                GetMemeDto(
                    id = "2",
                    name = "Meme 2",
                    url = "https://www.example.com/meme2.jpg",
                    width = 2,
                    height = 2,
                    box_count = 2
                )
            )
        )
        val memeResponse = GetMemesResponse(data = memeList)
        coEvery { memeService.fetchMemes() } returns memeResponse

        // When
        val result = memeRepository.getMemes()

        // Then
        assertEquals(memeList.memes, result)
    }

    @Test
    fun `createAndSaveMeme should return success when meme is created and saved`() = runBlocking {
        // Given
        val templateId = "123"
        val username = "testUser"
        val password = "testPass"
        val topText = "Top"
        val bottomText = "Bottom"
        val memeUrl = "https://www.example.com/meme.jpg"
        val createMemeResponse = PostMemeResponse(
            success = true, data = PostMemeDto(url = memeUrl),
            error_message = ""
        )
        coEvery { memeService.createMeme(templateId, username, password, topText, bottomText) } returns createMemeResponse

        // When
        val result = memeRepository.createAndSaveMeme(templateId, username, password, topText, bottomText)

        // Then
        assertTrue(result.isSuccess)
        coVerify { memeDao.insertMeme(any()) }
    }

    @Test
    fun `createAndSaveMeme returns failure when response is unsuccessful`() = runBlocking {
        // Arrange
        val templateId = "123"
        val username = "testUser"
        val password = "password"
        val topText = "Top Text"
        val bottomText = "Bottom Text"
        val errorMessage = "Error occurred"
        val memeResponse = PostMemeResponse(
            success = false,
            data = PostMemeDto(url = ""),
            error_message = errorMessage
        )
        coEvery { memeService.createMeme(templateId, username, password, topText, bottomText) } returns memeResponse

        // Act
        val result = memeRepository.createAndSaveMeme(templateId, username, password, topText, bottomText)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(errorMessage, result.exceptionOrNull()?.message)
        coVerify(exactly = 0) { memeDao.insertMeme(any()) }
    }

    @Test
    fun `createAndSaveMeme returns failure when exception is thrown`() = runBlocking {
        // Arrange
        val templateId = "123"
        val username = "testUser"
        val password = "password"
        val topText = "Top Text"
        val bottomText = "Bottom Text"
        val exception = Exception("Network error")
        coEvery { memeService.createMeme(templateId, username, password, topText, bottomText) } throws exception

        // Act
        val result = memeRepository.createAndSaveMeme(templateId, username, password, topText, bottomText)

        // Assert
        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
        coVerify(exactly = 0) { memeDao.insertMeme(any()) }
    }

    @Test
    fun `getLastMemeForUser returns correct LiveData`() {
        // Arrange
        val username = "testUser"
        val expectedMeme = Meme(imageUrl = "http://example.com/meme.png", username = username)
        val liveData = MutableLiveData<Meme>().apply { value = expectedMeme }

        every { memeDao.getLastMemeForUser(username) } returns liveData

        val observer = mockk<Observer<Meme>>(relaxed = true)

        // Act
        val result = memeRepository.getLastMemeForUser(username)
        result.observeForever(observer)

        // Assert
        assertEquals(expectedMeme, result.value)
        verify { observer.onChanged(expectedMeme) }
        verify { memeDao.getLastMemeForUser(username) }
    }
}