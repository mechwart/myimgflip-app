package com.example.myimgflipapp.ui.screens

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import app.cash.turbine.test
import com.example.myimgflipapp.data.db.Meme
import com.example.myimgflipapp.data.remote.dto.GetMemeDto
import com.example.myimgflipapp.data.repository.MemeRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@ExperimentalCoroutinesApi
class MemeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val memeRepository = mockk<MemeRepository>(relaxed = true)
    private lateinit var viewModel: MemeViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MemeViewModel(memeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchMemes updates memes StateFlow`() = runTest {
        // Arrange
        val expectedMemes = listOf(
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
        coEvery { memeRepository.getMemes() } returns expectedMemes

        // Act
        viewModel.memes.test {
            // Wait for fetchMemes to trigger
            val initial = awaitItem() // Initial value (empty list)
            assertEquals(emptyList(), initial)

            // Allow fetchMemes to complete
            cancelAndConsumeRemainingEvents() // Clean up subscription
        }

        // Assert
        coVerify { memeRepository.getMemes() }
    }

    @Test
    fun `createMeme updates memeState on success`() = runTest {
        // Arrange
        val templateId = "123"
        val username = "testUser"
        val password = "password"
        val topText = "Top"
        val bottomText = "Bottom"
        val expectedMeme = Meme(imageUrl = "http://example.com/meme.png", username = username)

        coEvery {
            memeRepository.createAndSaveMeme(
                templateId,
                username,
                password,
                topText,
                bottomText
            )
        } returns Result.success(expectedMeme)

        // Act
        viewModel.createMeme(templateId, username, password, topText, bottomText)

        // Assert
        viewModel.memeState.value?.let { assertEquals(expectedMeme.username, it.username) }
        // Assert
        viewModel.memeState.value?.let { assertEquals(expectedMeme, it) }
    }

    @Test
    fun `getLastMeme returns LiveData with the correct meme`() {
        // Arrange
        val username = "testUser"
        val expectedMeme = Meme(imageUrl = "http://example.com/meme.png", username = username)

        val liveData = MutableLiveData<Meme>().apply { value = expectedMeme }
        every { memeRepository.getLastMemeForUser(username) } returns liveData

        val observer = mockk<Observer<Meme>>(relaxed = true)

        // Act
        val result: LiveData<Meme> = viewModel.getLastMeme(username)
        result.observeForever(observer)

        // Assert
        assertEquals(expectedMeme, result.value)
        verify { observer.onChanged(expectedMeme) }
        verify { memeRepository.getLastMemeForUser(username) }
    }

    @Test
    fun `createMeme handles failure correctly`() = runTest {
        // Arrange
        val templateId = "123"
        val username = "testUser"
        val password = "password"
        val topText = "Top"
        val bottomText = "Bottom"
        val expectedException = Exception("Meme creation failed")

        coEvery { memeRepository.createAndSaveMeme(
            templateId, username, password, topText, bottomText)
        } returns Result.failure(expectedException)

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        // Act
        viewModel.createMeme(templateId, username, password, topText, bottomText)

        // Assert
        assertNull(viewModel.memeState.value)
        verify { Log.e("MemeViewModel", "Error: ${expectedException.message}") }
    }
}