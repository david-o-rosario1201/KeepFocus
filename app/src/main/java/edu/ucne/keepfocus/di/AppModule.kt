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

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun providesKeepFocusDatabase(@ApplicationContext appContext: Context): KeepFocusDatabase{
        return Room.databaseBuilder(
            appContext,
            KeepFocusDatabase::class.java,
            "KeepFocusDatabase"
        ).fallbackToDestructiveMigration().build()
    }
}