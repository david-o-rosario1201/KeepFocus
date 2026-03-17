package edu.ucne.keepfocus.presentation.focus

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.keepfocus.R
import edu.ucne.keepfocus.data.system.AppIconProvider
import edu.ucne.keepfocus.domain.models.App
import edu.ucne.keepfocus.domain.models.FocusZone
import edu.ucne.keepfocus.domain.usecases.DeleteFocusZoneUseCase
import edu.ucne.keepfocus.domain.usecases.GetInstalledAppsUseCase
import edu.ucne.keepfocus.domain.usecases.ObserveFocusZoneByIdUseCase
import edu.ucne.keepfocus.domain.usecases.ObserveFocusZoneWithAppsUseCase
import edu.ucne.keepfocus.domain.usecases.ObserveFocusZonesWithAppsUseCase
import edu.ucne.keepfocus.domain.usecases.UpsertFocusZoneWithApps
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FocusViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val appIconProvider: AppIconProvider,
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val upsertFocusZoneWithApps: UpsertFocusZoneWithApps,
    private val deleteFocusZone: DeleteFocusZoneUseCase,
    private val observeFocusZoneByIdUseCase: ObserveFocusZoneByIdUseCase,
    private val observeFocusZoneWithAppsUseCase: ObserveFocusZoneWithAppsUseCase,
    private val observeFocusZonesWithAppsUseCase: ObserveFocusZonesWithAppsUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(FocusUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<FocusUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val focusId: Int = savedStateHandle["focusId"] ?: 0

    init {
        getInstalledApps()
        onEvent(FocusUiEvent.OnSelectedFocus(focusId))
        removeSelectedAppsFromInstalledApps()
    }

    private fun getInstalledApps(){
        viewModelScope.launch {
            val installedApps = getInstalledAppsUseCase()
            val apps = installedApps.map { app ->
                val imageBitmap = appIconProvider.getIcon(app.packageName)
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

    private fun removeSelectedAppsFromInstalledApps(){
        viewModelScope.launch {
            observeFocusZonesWithAppsUseCase().collect{ focusZoneWithApps ->
                val usedPackages = focusZoneWithApps
                    .filter { it.focusZone.focusZoneId != focusId }
                    .flatMap { it.apps }
                    .map { it.packageName }
                    .toSet()

                val filteredApps = _uiState.value.installedApps.filter { app ->
                    app.packageName !in usedPackages
                }

                _uiState.update {
                    it.copy(installedApps = filteredApps)
                }
            }
        }
    }

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
                        focusZoneId = focusId,
                        nombre = state.nombre,
                        icono = state.icono,
                        tiempoLimite = state.tiempoLimite
                    )

                    val apps = state.selectedApps.map {
                        App(
                            packageName = it.packageName,
                            name = it.name,
                            isBlocked = false,
                            dailyLimitMinutes = 0,
                        )
                    }

                    try{
                        upsertFocusZoneWithApps(
                            focusZone = focusZone,
                            apps = apps
                        )
                        sendEffect(FocusUiEffect.NavigateBackWithMessage("Focus guardado con éxito"))
                    } catch (e: Exception){
                        Log.e("FocusSave", "Error saving focus", e)
                        sendEffect(FocusUiEffect.ShowSnackbar("Error al guardar el Focus Zone"))
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
            is FocusUiEvent.ToggleAppSelection -> {
                _uiState.update { state ->
                    val updatedApps = state.installedApps.map { app ->
                        if(app.packageName == event.packageName){
                            app.copy(isSelected = !app.isSelected)
                        } else {
                            app
                        }
                    }

                    val selectedApps = updatedApps.filter { it.isSelected }

                    state.copy(
                        installedApps = updatedApps,
                        selectedApps = selectedApps
                    )
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
            is FocusUiEvent.OnDeleteFocus -> {
                viewModelScope.launch {
                    val focusZone = observeFocusZoneByIdUseCase(event.focusId).first()

                    if(focusZone != null){
                        deleteFocusZone(focusZone)
                    } else{
                        _uiEffect.emit(FocusUiEffect.ShowSnackbar("No se encontró el Focus"))
                    }
                }
            }
            is FocusUiEvent.OnSelectedFocus -> {
                viewModelScope.launch {
                    val focusZoneWithApps = observeFocusZoneWithAppsUseCase(event.focusId).first()

                    val apps = focusZoneWithApps?.apps?.map { app ->
                        val imageBitmap = appIconProvider.getIcon(app.packageName)
                        AppUi(
                            packageName = app.packageName,
                            name = app.name,
                            isSelected = true,
                            icon = imageBitmap
                        )
                    }

                    val updatedInstalledApps = _uiState.value.installedApps.map { app ->
                        if(apps?.any {
                                it.packageName == app.packageName && it.isSelected
                            } == true){
                            app.copy(isSelected = true)
                        } else{
                            app.copy(isSelected = false)
                        }
                    }

                    _uiState.update {
                        it.copy(
                            nombre = focusZoneWithApps?.focusZone?.nombre ?: "",
                            icono = focusZoneWithApps?.focusZone?.icono ?: R.drawable.ic_launcher_foreground,
                            tiempoLimite = focusZoneWithApps?.focusZone?.tiempoLimite ?: 0L,
                            selectedApps = apps ?: emptyList(),
                            installedApps = updatedInstalledApps
                        )
                    }
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