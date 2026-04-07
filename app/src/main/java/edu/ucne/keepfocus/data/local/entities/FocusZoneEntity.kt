package edu.ucne.keepfocus.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FocusZones")
data class FocusZoneEntity(
    @PrimaryKey(autoGenerate = true)
    val focusZoneId: Int = 0,
    val nombre: String,
    val icono: Int,
    val tiempoLimite: Long,
    val progreso: Long = 0L
)