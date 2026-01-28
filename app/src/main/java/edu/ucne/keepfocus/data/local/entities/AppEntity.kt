package edu.ucne.keepfocus.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Apps")
data class AppEntity(
    @PrimaryKey
    val packageName: String,
    val name: String,
    val isBlocked: Boolean,
    val dailyLimitMinutes: Int
)