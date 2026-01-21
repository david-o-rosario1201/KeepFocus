package edu.ucne.keepfocus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

@Composable
fun BottomNavBar(
    navController: NavHostController
){

}

private data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val screen: Screen
)

private fun getBottomNavItems(): List<BottomNavItem>{
    TODO()
}