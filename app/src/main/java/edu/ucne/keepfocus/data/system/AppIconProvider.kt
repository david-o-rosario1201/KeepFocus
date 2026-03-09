package edu.ucne.keepfocus.data.system

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppIconProvider @Inject constructor(
    @ApplicationContext private val context: Context
){

    private  val cache = mutableMapOf<String, ImageBitmap?>()

    fun getIcon(packageName: String): ImageBitmap? {
        return cache.getOrPut(packageName){

            val drawable = runCatching {
                context.packageManager.getApplicationIcon(packageName)
            }.getOrNull()

            drawable?.toBitmap()?.asImageBitmap()
        }
    }
}