package edu.ucne.keepfocus.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "DetalleFocusZoneApp",
    primaryKeys = ["focusZoneId", "appId"],
    foreignKeys = [
        ForeignKey(
            entity = FocusZoneEntity::class,
            parentColumns = ["focusZoneId"],
            childColumns = ["focusZoneId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AppEntity::class,
            parentColumns = ["appId"],
            childColumns = ["appId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("focusZoneId"),
        Index("appId")
    ]
)
data class DetalleFocusZoneAppEntity(
    val focusZoneId: Int,
    val appId: Int
)