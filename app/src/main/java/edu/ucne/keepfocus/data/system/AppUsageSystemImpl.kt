package edu.ucne.keepfocus.data.system

import android.app.usage.UsageStatsManager
import android.content.Context
import edu.ucne.keepfocus.domain.system.AppUsageSystem
import javax.inject.Inject

class AppUsageSystemImpl @Inject constructor(
    private val context: Context
): AppUsageSystem {

    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    override fun getUsageTime(packageName: String, start: Long, end: Long): Long {
        TODO("Not yet implemented")
    }
}