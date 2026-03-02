package edu.ucne.keepfocus.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.keepfocus.presentation.focus.FocusScreen
import edu.ucne.keepfocus.presentation.home.HomeScreen

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable<Screen.HomeScreen>{
                HomeScreen(
                    navController = navController,
                    snackbarHostState = snackbarHostState
                )
            }
            composable<Screen.FocusScreen> {
                FocusScreen(
                    navController = navController,
                    snackbarHostState = snackbarHostState
                )
            }
            composable<Screen.SettingScreen> { }
        }
    }
}