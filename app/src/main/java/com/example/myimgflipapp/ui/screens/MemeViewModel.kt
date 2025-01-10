package com.example.myimgflipapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myimgflipapp.data.remote.dto.MemeDto
import com.example.myimgflipapp.data.repository.MemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemeViewModel @Inject constructor(private val memeRepository: MemeRepository) : ViewModel() {

    private val _memes = MutableStateFlow<List<MemeDto>>(emptyList())
    val memes: StateFlow<List<MemeDto>> get() = _memes

    init {
        fetchMemes()
    }

    private fun fetchMemes() {
        viewModelScope.launch {
            _memes.value = memeRepository.getMemes()
        }
    }
}
