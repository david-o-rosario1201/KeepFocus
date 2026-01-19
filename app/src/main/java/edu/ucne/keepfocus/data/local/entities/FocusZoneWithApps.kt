package edu.ucne.keepfocus.data.local.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class FocusZoneWithApps(
    @Embedded
    val focusZone: FocusZoneEntity,
    @Relation(
        parentColumn = "focusZoneId",
        entityColumn = "appId",
        associateBy = Junction(DetalleFocusZoneApp::class)
    )
    val apps: List<AppEntity>
)