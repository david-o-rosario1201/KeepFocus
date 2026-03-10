package edu.ucne.keepfocus.presentation.focus

import androidx.annotation.DrawableRes
import edu.ucne.keepfocus.R

data class FocusUiState(
    val nombre: String = "",
    @DrawableRes val icono: Int = R.drawable.ic_launcher_foreground,
    val tiempoLimite: Long = 0,
    val installedApps: List<AppUi> = emptyList(),
    val selectedApps: List<AppUi> = emptyList(),
    val overlay: FocusOverlay = FocusOverlay.None
){
    val isEmpty: Boolean get() = selectedApps.isEmpty()
    val isCompleted: Boolean
        get() = nombre.isNotEmpty() && icono != R.drawable.ic_launcher_foreground
                && tiempoLimite != 0L && selectedApps.isNotEmpty()
    val hasUnSavedChanges
        get() = nombre.isNotEmpty() || icono != R.drawable.ic_launcher_foreground
                || tiempoLimite != 0L || selectedApps.isNotEmpty()
}