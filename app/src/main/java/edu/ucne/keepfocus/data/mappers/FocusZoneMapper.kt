package edu.ucne.keepfocus.data.mappers

import edu.ucne.keepfocus.data.local.entities.FocusZoneEntity
import edu.ucne.keepfocus.domain.models.FocusZone

fun FocusZoneEntity.toDomain(): FocusZone = FocusZone(
    focusZoneId = focusZoneId,
    nombre = nombre,
    icono = icono,
    tiempoLimite = tiempoLimite
)

fun FocusZone.asEntity(): FocusZoneEntity = FocusZoneEntity(
    focusZoneId = focusZoneId,
    nombre = nombre,
    icono = icono,
    tiempoLimite = tiempoLimite
)