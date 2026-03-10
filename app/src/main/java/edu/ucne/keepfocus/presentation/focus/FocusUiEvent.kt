package edu.ucne.keepfocus.presentation.focus

sealed interface FocusUiEvent {
    data object OnDismissOverlay: FocusUiEvent
    data object Save: FocusUiEvent
    data object OnNavigationAttempt: FocusUiEvent
    data object OnConfirmExit: FocusUiEvent
    data class OnShowOverlay(val overlay: FocusOverlay): FocusUiEvent
    data class ToggleAppSelection(val packageName: String): FocusUiEvent
    data class OnNombreChange(val nombre: String): FocusUiEvent
    data class OnTiempoLimiteChange(val tiempoLimite: Long): FocusUiEvent
    data class OnIconoChange(val icono: Int): FocusUiEvent
    data class OnDeleteFocus(val focusId: Int): FocusUiEvent
    data class OnSelectedFocus(val focusId: Int): FocusUiEvent
}