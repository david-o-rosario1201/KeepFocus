package edu.ucne.keepfocus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun KeepFocusNavHost(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ""
    ) {

    }
}