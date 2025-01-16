package com.example.myimgflipapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.myimgflipapp.data.db.Meme

@Composable
fun MemeResultScreen(viewModel: MemeViewModel, username: String, navigateToHomeScreen: () -> Unit) {
    val meme by viewModel.getLastMeme(username).observeAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        MemeResultScreenContent(meme, navigateToHomeScreen)
    }
}

@Composable
fun MemeResultScreenContent(meme: Meme?, navigateToHomeScreen: () -> Unit) {
    if (meme == null) {
        Text(text = "Not available meme", style = MaterialTheme.typography.h6)
    } else {
        Column {
            Text(text = "Here is your created meme",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h5)
            AsyncImage(
                model = meme.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(top = 16.dp)
            )
            Button(
                onClick = { navigateToHomeScreen() },
                modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Back to Home Page")
            }
        }
    }
}