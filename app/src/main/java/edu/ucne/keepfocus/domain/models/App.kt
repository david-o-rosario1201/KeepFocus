package edu.ucne.keepfocus.domain.models

import android.graphics.drawable.Drawable

data class App(
    val packageName: String,
    val name: String,
    val icon: Drawable?,
    val isBlocked: Boolean,
    val dailyLimitMinutes: Int
)