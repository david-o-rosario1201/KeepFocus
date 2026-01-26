package edu.ucne.keepfocus.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.keepfocus.data.local.dao.AppDao
import edu.ucne.keepfocus.data.local.dao.DetalleFocusZoneAppDao
import edu.ucne.keepfocus.data.local.dao.FocusZoneDao
import edu.ucne.keepfocus.data.local.entities.AppEntity
import edu.ucne.keepfocus.data.local.entities.DetalleFocusZoneAppEntity
import edu.ucne.keepfocus.data.local.entities.FocusZoneEntity

@Database(
    entities = [
        AppEntity::class,
        FocusZoneEntity::class,
        DetalleFocusZoneAppEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class KeepFocusDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
    abstract fun focusZoneDao(): FocusZoneDao
    abstract fun detalleFocusZoneAppDao(): DetalleFocusZoneAppDao
}