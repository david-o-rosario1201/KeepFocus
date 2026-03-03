package edu.ucne.keepfocus.domain.models

data class FocusZone(
    val focusZoneId: Int? = null,
    val nombre: String = "",
    val icono:Int = 0,
    val tiempoLimite: Long = 0L
)