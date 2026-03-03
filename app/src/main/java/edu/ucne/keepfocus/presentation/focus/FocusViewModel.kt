package edu.ucne.keepfocus.presentation.focus

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.keepfocus.domain.models.FocusZone
import edu.ucne.keepfocus.domain.system.AppIconProvider
import edu.ucne.keepfocus.domain.usecases.GetInstalledAppsUseCase
import edu.ucne.keepfocus.domain.usecases.UpsertFocusZone
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FocusViewModel @Inject constructor(
    private val appIconProvider: AppIconProvider,
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val upsertFocusZone: UpsertFocusZone
): ViewModel(){
    private val _uiState = MutableStateFlow(FocusUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<FocusUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        getInstalledApps()
    }

    private fun getInstalledApps(){
        viewModelScope.launch {
            val installedApps = getInstalledAppsUseCase()
            val apps = installedApps.map { app ->
                val drawable = getIcon(app.packageName)
                val imageBitmap = drawable
                    ?.toBitmap()
                    ?.asImageBitmap()

                AppUi(
                    packageName = app.packageName,
                    name = app.name,
                    icon = imageBitmap,
                    isSelected = false
                )
            }

            _uiState.update {
                it.copy(installedApps = apps)
            }
        }
    }

    private fun getIcon(packageName: String) = appIconProvider.getIcon(packageName)

    private fun sendEffect(effect: FocusUiEffect){
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }

    fun onEvent(event: FocusUiEvent){
        when(event){
            FocusUiEvent.OnDismissOverlay -> {
                _uiState.update {
                    it.copy(overlay = FocusOverlay.None)
                }
            }
            FocusUiEvent.Save -> {
                viewModelScope.launch {
                    val state = _uiState.value
                    val focusZone = FocusZone(
                        nombre = state.nombre,
                        icono = state.icono,
                        tiempoLimite = state.tiempoLimite,
                    )
                    try {
                        upsertFocusZone(focusZone)
                        sendEffect(FocusUiEffect.NavigateBackWithMessage("Focus guardado con éxito"))
                    } catch (e: Exception){
                        _uiEffect.emit(FocusUiEffect.ShowSnackbar("Error al guardar el Focus Zone"))
                    }
                }
            }
            FocusUiEvent.OnNavigationAttempt -> {
                viewModelScope.launch {
                    if(_uiState.value.hasUnSavedChanges){
                        _uiState.update {
                            it.copy(overlay = FocusOverlay.Exit)
                        }
                    } else{
                        _uiEffect.emit(FocusUiEffect.AllowNavigation)
                    }
                }
            }
            FocusUiEvent.OnConfirmExit -> {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(overlay = FocusOverlay.None)
                    }
                    _uiEffect.emit(FocusUiEffect.AllowNavigation)
                }
            }
            is FocusUiEvent.OnShowOverlay -> {
                _uiState.update {
                    it.copy(overlay = event.overlay)
                }
            }
            is FocusUiEvent.OnSelectedApp -> {
                _uiState.update { state ->
                    val updatedApps = state.installedApps.map { app ->
                        if(app.packageName == event.packageName){
                            app.copy(isSelected = !app.isSelected)
                        } else {
                            app
                        }
                    }
                    state.copy(installedApps = updatedApps)
                }
            }
            is FocusUiEvent.OnDeleteSelectedApp -> {
                _uiState.update { state ->
                    val apps = state.installedApps.map { app ->
                        if(app.packageName == event.packageName){
                            app.copy(isSelected = false)
                        } else {
                            app
                        }
                    }
                    state.copy(installedApps = apps)
                }
            }
            is FocusUiEvent.OnNombreChange -> {
                _uiState.update {
                    it.copy(nombre = event.nombre)
                }
            }
            is FocusUiEvent.OnTiempoLimiteChange -> {
                _uiState.update {
                    it.copy(tiempoLimite = event.tiempoLimite)
                }
            }
            is FocusUiEvent.OnIconoChange -> {
                _uiState.update {
                    it.copy(icono = event.icono)
                }
            }
        }
    }
}

data class AppUi(
    val packageName: String,
    val name: String,
    val icon: ImageBitmap?,
    val isSelected: Boolean = false
)