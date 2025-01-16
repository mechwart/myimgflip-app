package com.example.myimgflipapp.ui.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myimgflipapp.data.db.Meme
import com.example.myimgflipapp.data.remote.dto.GetMemeDto
import com.example.myimgflipapp.data.repository.MemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemeViewModel @Inject constructor(private val memeRepository: MemeRepository) : ViewModel() {

    private val _memes = MutableStateFlow<List<GetMemeDto>>(emptyList())
    val memes: StateFlow<List<GetMemeDto>> get() = _memes

    private val _memeState = mutableStateOf<Meme?>(null)

    init {
        fetchMemes()
    }

    private fun fetchMemes() {
        viewModelScope.launch {
            _memes.value = memeRepository.getMemes()
        }
    }

    fun createMeme(
        templateId: String,
        username: String,
        password: String,
        topText: String,
        bottomText: String
    ) {
        viewModelScope.launch {

            val result = memeRepository.createAndSaveMeme(
                templateId, username, password, topText, bottomText
            )

            result.onSuccess { meme ->
                _memeState.value = meme
            }.onFailure { exception ->
                Log.e("MemeViewModel", "Error: ${exception.message}")
            }
        }
    }

    fun getLastMeme(username: String): LiveData<Meme> {
        return memeRepository.getLastMemeForUser(username)
    }
}
