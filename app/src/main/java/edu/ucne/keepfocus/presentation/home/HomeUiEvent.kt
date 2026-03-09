package edu.ucne.keepfocus.presentation.home

sealed interface HomeUiEvent {
    data class OnDeleteFocus(val focusId: Int): HomeUiEvent
    data class OnEditFocus(val focusId: Int): HomeUiEvent
}