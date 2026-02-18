package edu.ucne.keepfocus.presentation.focus

data class FocusUiState(
    val nombre: String = "",
    val icono: String = "",
    val tiempoLimite: Long = 0,
    val installedApps: List<AppUi> = emptyList(),
    val selectedApps: List<AppUi> = emptyList(),
    val overlay: FocusOverlay = FocusOverlay.None
){
    val isEmpty: Boolean get() = selectedApps.isEmpty()
}