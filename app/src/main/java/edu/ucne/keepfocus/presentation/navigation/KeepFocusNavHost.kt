package edu.ucne.keepfocus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun KeepFocusNavHost(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = ""
    ) { }
}