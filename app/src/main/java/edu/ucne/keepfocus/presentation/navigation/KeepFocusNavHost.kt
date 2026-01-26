package edu.ucne.keepfocus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun KeepFocusNavHost(){
    val rootNavController = rememberNavController()
    NavHost(
        navController = rootNavController,
        startDestination = Screen.MainScreen.route
    ) {
        composable<Screen.MainScreen>{
            MainScreen()
        }
    }
}