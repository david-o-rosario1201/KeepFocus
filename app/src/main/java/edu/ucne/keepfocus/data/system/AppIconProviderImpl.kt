package edu.ucne.keepfocus.data.system

import android.content.Context
import android.graphics.drawable.Drawable
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.ucne.keepfocus.domain.system.AppIconProvider
import javax.inject.Inject

class AppIconProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
): AppIconProvider {

    private  val cache = mutableMapOf<String, Drawable?>()

    override fun getIcon(packageName: String): Drawable? {
        return cache.getOrPut(packageName){
            runCatching {
                context.packageManager.getApplicationIcon(packageName)
            }.getOrNull()
        }
    }
}