package edu.ucne.keepfocus.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.keepfocus.data.system.AppIconProvider
import edu.ucne.keepfocus.domain.usecases.DeleteFocusZoneUseCase
import edu.ucne.keepfocus.domain.usecases.ObserveFocusZoneByIdUseCase
import edu.ucne.keepfocus.domain.usecases.ObserveFocusZonesWithAppsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeFocusZonesWithApps: ObserveFocusZonesWithAppsUseCase,
    private val appIconProvider: AppIconProvider,
    private val observeFocusZoneByIdUseCase: ObserveFocusZoneByIdUseCase,
    private val deleteFocusZoneUseCase: DeleteFocusZoneUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<HomeUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        observeFocusZones()
    }

    private fun observeFocusZones(){
        viewModelScope.launch {
            observeFocusZonesWithApps()
                .collect { focusZones ->
                    _uiState.update {
                        it.copy(focusZonesWithApps = focusZones)
                    }
                }
        }
    }
    fun getIcon(packageName: String) = appIconProvider.getIcon(packageName)

    fun onEvent(event: HomeUiEvent){
        when(event){
            is HomeUiEvent.OnDeleteFocus -> {
                viewModelScope.launch {
                    val focusZone = observeFocusZoneByIdUseCase(event.focusId).first()

                    if(focusZone != null){
                        deleteFocusZoneUseCase(focusZone)
                    } else{
                        _uiEffect.emit(HomeUiEffect.ShowSnackbar("No se encontró el Focus"))
                    }
                }
            }
            is HomeUiEvent.OnEditFocus -> {
                viewModelScope.launch {
                    _uiEffect.emit(HomeUiEffect.NavigateToEdit(event.focusId))
                }
            }
        }
    }
}