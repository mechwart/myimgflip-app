package com.example.myimgflipapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myimgflipapp.ui.screens.AboutScreen
import com.example.myimgflipapp.ui.screens.HomeScreen
import com.example.myimgflipapp.ui.screens.MemeResultScreen
import com.example.myimgflipapp.ui.screens.MemeScreen
import com.example.myimgflipapp.ui.screens.MemeViewModel

@Composable
fun Navigation(viewModel: MemeViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "homeScreen") {
        composable("memeScreen") {
            MemeScreen(viewModel = viewModel, navigateToResult = { username ->
                navController.navigate("resultScreen/$username")
            })
        }
        composable("aboutScreen") {
            AboutScreen()
        }
        composable("resultScreen/{username}",
            arguments = listOf(navArgument("username") {type = NavType.StringType})) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            MemeResultScreen(viewModel = viewModel, username = username, navigateToHomeScreen = {
                navController.navigate("homeScreen")
            })
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