package edu.ucne.keepfocus.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object MainScreen: Screen()

    @Serializable
    data object HomeScreen: Screen()

    @Serializable
    data object AddFocusZoneScreen: Screen()

    @Serializable
    data object SettingScreen: Screen()
}

val Screen.route: String get() = this::class.qualifiedName!!