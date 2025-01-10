package com.example.myimgflipapp.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.myimgflipapp.data.remote.dto.MemeDto


@Composable
fun MemeScreen(viewModel: MemeViewModel = hiltViewModel(), modifier: Modifier) {
    val memes = viewModel.memes.collectAsState(initial = emptyList())



    LazyColumn(modifier = modifier) {
        items(memes.value) { meme ->
            MemeItem(meme = meme)
        }
    }
}

@Composable
fun MemeItem(meme: MemeDto) {
    AsyncImage(
        model = meme.url,
        contentDescription = meme.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .size(200.dp)
    )
    Text(text = meme.name)
}