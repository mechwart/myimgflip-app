package com.example.myimgflipapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myimgflipapp.ui.navigation.Navigation
import com.example.myimgflipapp.ui.screens.MemeViewModel
import com.example.myimgflipapp.ui.theme.MyImgflipAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MemeViewModel = viewModel()
            MyImgflipAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Navigation(viewModel = viewModel)
                }
            }
        }
    }
}

