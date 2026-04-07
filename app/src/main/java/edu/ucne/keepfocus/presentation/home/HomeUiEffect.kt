package edu.ucne.keepfocus.presentation.home

sealed interface HomeUiEffect {
    data class ShowSnackbar(val message: String): HomeUiEffect
    data class NavigateToEdit(val focusId: Int): HomeUiEffect
    data class NavigateToDetails(val focusId: Int): HomeUiEffect
}