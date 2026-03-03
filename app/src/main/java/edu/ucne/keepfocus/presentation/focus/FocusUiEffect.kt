package edu.ucne.keepfocus.presentation.focus

sealed interface FocusUiEffect {
    data object AllowNavigation: FocusUiEffect
    data class NavigateBackWithMessage(val message: String): FocusUiEffect
    data class ShowSnackbar(val message: String): FocusUiEffect
}