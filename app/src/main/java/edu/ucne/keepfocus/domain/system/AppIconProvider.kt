package edu.ucne.keepfocus.domain.system

import android.graphics.drawable.Drawable

interface AppIconProvider {
    fun getIcon(packageName: String): Drawable?
}