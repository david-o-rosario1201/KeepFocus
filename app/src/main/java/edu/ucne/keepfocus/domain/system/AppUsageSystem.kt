package edu.ucne.keepfocus.domain.system

interface AppUsageSystem {
    fun getUsageTime(packageName: String, start: Long, end: Long): Long
}