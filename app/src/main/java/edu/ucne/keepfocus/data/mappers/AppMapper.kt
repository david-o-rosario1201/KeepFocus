package edu.ucne.keepfocus.data.mappers

import edu.ucne.keepfocus.data.local.entities.AppEntity
import edu.ucne.keepfocus.domain.models.App

fun AppEntity.toDomain(): App = App(
    packageName = packageName,
    name = name,
    icon = null,
    isBlocked = isBlocked,
    dailyLimitMinutes = dailyLimitMinutes
)

fun App.asEntity(): AppEntity = AppEntity(
    packageName = packageName,
    name = name,
    isBlocked = isBlocked,
    dailyLimitMinutes = dailyLimitMinutes
)