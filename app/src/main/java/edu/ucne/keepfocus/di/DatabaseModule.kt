package edu.ucne.keepfocus.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.keepfocus.data.local.database.KeepFocusDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesKeepFocusDatabase(@ApplicationContext appContext: Context): KeepFocusDatabase {
        return Room.databaseBuilder(
            appContext,
            KeepFocusDatabase::class.java,
            "KeepFocusDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun providesAppDao(keepFocusDb: KeepFocusDatabase) = keepFocusDb.appDao()

    @Provides
    @Singleton
    fun providesFocusZoneDao(keepFocusDb: KeepFocusDatabase) = keepFocusDb.focusZoneDao()

    @Provides
    @Singleton
    fun providesDetalleFocusZoneAppDao(keepFocusDb: KeepFocusDatabase) = keepFocusDb.detalleFocusZoneAppDao()
}