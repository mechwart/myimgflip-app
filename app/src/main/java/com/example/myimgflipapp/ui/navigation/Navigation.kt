package com.example.myimgflipapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myimgflipapp.ui.screens.AboutScreen
import com.example.myimgflipapp.ui.screens.HomeScreen
import com.example.myimgflipapp.ui.screens.MemeScreen

@Composable
fun Navigation(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "homeScreen") {
        composable("memeScreen") {
            MemeScreen(modifier = modifier)
        }
        composable("aboutScreen") {
            AboutScreen()
        }
        composable("homeScreen") {
            HomeScreen(navigationToMakeMemes = {
                navController.navigate("memeScreen")
            }, navigationToAbout = {
                navController.navigate("aboutScreen")
            })
        }
    }
}