package edu.ucne.keepfocus.presentation.focus

data class FocusUiState(
    val nombre: String = "",
    val icono: String = "",
    val tiempoLimite: Long = 0,
    val installedApps: List<AppUi> = emptyList(),
    val showAppPickerModal: Boolean = false,
    val selectedApps: List<AppUi> = emptyList()
){
    val isEmpty: Boolean get() = selectedApps.isEmpty()
}