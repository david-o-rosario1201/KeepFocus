package edu.ucne.keepfocus.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.keepfocus.domain.usecases.GetFocusZonesWithApps
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeFocusZonesWithApps: GetFocusZonesWithApps
): ViewModel(){
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

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
}