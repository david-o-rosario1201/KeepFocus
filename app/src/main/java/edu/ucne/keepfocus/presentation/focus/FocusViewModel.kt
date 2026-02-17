package edu.ucne.keepfocus.presentation.focus

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.keepfocus.domain.system.AppIconProvider
import edu.ucne.keepfocus.domain.usecases.GetInstalledAppsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FocusViewModel @Inject constructor(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val appIconProvider: AppIconProvider
): ViewModel(){
    private val _uiState = MutableStateFlow(FocusUiState())
    val uiState = _uiState.asStateFlow()

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

    fun onEvent(event: FocusUiEvent){
        when(event){
            FocusUiEvent.OnOpenModal -> {
                _uiState.update {
                    it.copy(showAppPickerModal = true)
                }
            }
            FocusUiEvent.OnCloseModal -> {
                _uiState.update {
                    it.copy(showAppPickerModal = false)
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
            is FocusUiEvent.OnAddSelectedApps -> {
                _uiState.update {
                    it.copy(
                        selectedApps = event.apps,
                        showAppPickerModal = false
                    )
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
                    val selectedApps = apps.filter { app -> app.isSelected }
                    state.copy(
                        installedApps = apps,
                        selectedApps = selectedApps
                    )
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