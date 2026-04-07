package edu.ucne.keepfocus.domain.models

data class FocusZone(
    val focusZoneId: Int = 0,
    val nombre: String = "",
    val icono:Int = 0,
    val tiempoLimite: Long = 0L,
    val progreso: Long = 0L
)