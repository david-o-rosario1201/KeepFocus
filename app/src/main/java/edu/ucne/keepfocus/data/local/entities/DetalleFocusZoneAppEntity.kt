package edu.ucne.keepfocus.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "DetalleFocusZoneApp",
    primaryKeys = ["focusZoneId", "packageName"],
    foreignKeys = [
        ForeignKey(
            entity = FocusZoneEntity::class,
            parentColumns = ["focusZoneId"],
            childColumns = ["focusZoneId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AppEntity::class,
            parentColumns = ["packageName"],
            childColumns = ["packageName"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("focusZoneId"),
        Index("packageName")
    ]
)
data class DetalleFocusZoneAppEntity(
    val focusZoneId: Int,
    val packageName: String
)