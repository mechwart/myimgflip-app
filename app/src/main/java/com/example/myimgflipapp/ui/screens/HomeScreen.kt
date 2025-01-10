package com.example.myimgflipapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(navigationToMakeMemes: () -> Unit, navigationToAbout: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HomeContent(
            navigationToMakeMemes = navigationToMakeMemes,
            navigationToAbout = navigationToAbout
        )
    }

}

@Composable
fun HomeContent(navigationToMakeMemes: () -> Unit,
                navigationToAbout: () -> Unit) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "My Imgflip App", modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineLarge)
        Button(onClick = {navigationToMakeMemes()}) {
            Text(text = "Make a meme based on meme template")
        }
        Button(onClick = {navigationToAbout()}) {
            Text(text = "About")
        }
    }
}