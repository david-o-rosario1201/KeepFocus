package edu.ucne.keepfocus.presentation.home

import edu.ucne.keepfocus.domain.models.FocusZoneWithApps

data class HomeUiState(
    val focusZonesWithApps: List<FocusZoneWithApps> = emptyList()
){
    val isEmpty: Boolean get() = focusZonesWithApps.isEmpty()
}