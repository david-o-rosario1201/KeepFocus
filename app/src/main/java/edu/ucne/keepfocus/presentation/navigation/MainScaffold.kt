package edu.ucne.keepfocus.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MainScaffold(
    navController: NavHostController
){
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "",
            modifier = Modifier.padding(innerPadding)
        ){

        }
    }
}