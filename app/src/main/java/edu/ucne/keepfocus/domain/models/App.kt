package edu.ucne.keepfocus.domain.models

data class App(
    val packageName: String,
    val name: String,
    val isBlocked: Boolean,
    val dailyLimitMinutes: Int
)