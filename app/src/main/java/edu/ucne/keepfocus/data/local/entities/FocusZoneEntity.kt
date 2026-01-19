package edu.ucne.keepfocus.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FocusZones")
data class FocusZoneEntity(
    @PrimaryKey(autoGenerate = true)
    val focusZoneId: Int?,
    val nombre: String,
    val icono: String,
    val tiempoLimite: Long
)