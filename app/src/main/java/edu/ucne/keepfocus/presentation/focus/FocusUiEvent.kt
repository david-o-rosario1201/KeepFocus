package edu.ucne.keepfocus.presentation.focus

sealed interface FocusUiEvent {
    data object OnDismissOverlay: FocusUiEvent
    data class OnShowOverlay(val overlay: FocusOverlay): FocusUiEvent
    data class OnSelectedApp(val packageName: String): FocusUiEvent
    data class OnAddSelectedApps(val apps: List<AppUi>): FocusUiEvent
    data class OnDeleteSelectedApp(val packageName: String): FocusUiEvent
    data class OnNombreChange(val nombre: String): FocusUiEvent
    data class OnTiempoLimiteChange(val tiempoLimite: Long): FocusUiEvent
    data class OnIconoChange(val icono: Int): FocusUiEvent
}