package edu.ucne.keepfocus.data.mappers

import edu.ucne.keepfocus.data.local.entities.DetalleFocusZoneAppEntity
import edu.ucne.keepfocus.domain.models.DetalleFocusZoneApp

fun DetalleFocusZoneAppEntity.toDomain(): DetalleFocusZoneApp = DetalleFocusZoneApp(
    focusZoneId = focusZoneId,
    packageName = packageName
)

fun DetalleFocusZoneApp.asEntity(): DetalleFocusZoneAppEntity = DetalleFocusZoneAppEntity(
    focusZoneId = focusZoneId,
    packageName = packageName
)