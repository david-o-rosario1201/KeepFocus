package edu.ucne.keepfocus.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Apps")
data class AppEntity(
    @PrimaryKey(autoGenerate = true)
    val appId: Int?,
    val nombre: String,
    val icono: String
)