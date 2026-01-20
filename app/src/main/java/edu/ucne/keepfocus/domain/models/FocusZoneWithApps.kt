package edu.ucne.keepfocus.domain.models

data class FocusZoneWithApps(
    val focusZone: FocusZone,
    val apps: List<App>
)