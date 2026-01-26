package edu.ucne.keepfocus.domain.models

data class FocusZone(
    val focusZoneId: Int? = null,
    val nombre: String = "",
    val icono: String = "",
    val tiempoLimite: Long = 0L
)