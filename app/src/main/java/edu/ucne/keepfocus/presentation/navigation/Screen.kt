package edu.ucne.keepfocus.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object MainScreen: Screen()

    @Serializable
    data object HomeScreen: Screen()

    @Serializable
    data class FocusScreen(val focusId: Int = 0): Screen()

    @Serializable
    data object SettingScreen: Screen()
}

val Screen.route: String get() = this::class.qualifiedName!!