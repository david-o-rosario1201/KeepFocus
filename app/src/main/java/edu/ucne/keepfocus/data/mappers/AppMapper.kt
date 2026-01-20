package edu.ucne.keepfocus.data.mappers

import edu.ucne.keepfocus.data.local.entities.AppEntity
import edu.ucne.keepfocus.domain.models.App

fun AppEntity.toDomain(): App = App(
    appId = appId,
    nombre = nombre,
    icono = icono
)

fun App.asEntity(): AppEntity = AppEntity(
    appId = appId,
    nombre = nombre,
    icono = icono
)