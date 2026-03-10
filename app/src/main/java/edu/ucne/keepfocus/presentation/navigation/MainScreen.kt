package edu.ucne.keepfocus.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.keepfocus.presentation.focus.FocusScreen
import edu.ucne.keepfocus.presentation.home.HomeScreen

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    var pendingRoute by remember { mutableStateOf<String?>(null) }
    var focusNavigationAttempt by remember { mutableStateOf<(() -> Unit)?>(null) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                onNavigateRequest = { route ->

                    val currentRoute = navController.currentBackStackEntry?.destination?.route

                    if(currentRoute?.startsWith(Screen.FocusScreen::class.qualifiedName!!) == true){
                        pendingRoute = route
                        focusNavigationAttempt?.invoke()
                    } else {
                        navigate(navController,route)
                    }
                }
            )
        },
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
                    snackbarHostState = snackbarHostState,
                    // 👇 Focus le dice a Main cómo intentar salir
                    registerNavigationAttempt = {
                        focusNavigationAttempt = it
                    },
                    // 👇 Focus le dice a Main cuándo puede navegar
                    onAllowNavigation = {
                        pendingRoute?.let {
                            navigate(navController,it)
                            pendingRoute = null
                        }
                    }
                )
            }
            composable<Screen.SettingScreen> { }
        }
    }
}

private fun navigate(navController: NavHostController, route: String){
    navController.navigate(route){
        launchSingleTop = true
        restoreState = true

        if(route == Screen.HomeScreen.route){
            popUpTo(Screen.HomeScreen.route){ inclusive = true }
        }
    }
}