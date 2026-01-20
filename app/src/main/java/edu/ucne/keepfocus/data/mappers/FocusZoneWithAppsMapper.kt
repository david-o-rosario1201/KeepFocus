package edu.ucne.keepfocus.data.mappers

import edu.ucne.keepfocus.data.local.entities.FocusZoneWithAppsEntity
import edu.ucne.keepfocus.domain.models.FocusZoneWithApps

fun FocusZoneWithAppsEntity.toDomain(): FocusZoneWithApps = FocusZoneWithApps(
    focusZone = focusZone.toDomain(),
    apps = apps.map { it.toDomain() }
)

fun FocusZoneWithApps.asEntity(): FocusZoneWithAppsEntity = FocusZoneWithAppsEntity(
    focusZone = focusZone.asEntity(),
    apps = apps.map { it.asEntity() }
)