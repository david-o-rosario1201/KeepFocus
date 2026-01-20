package edu.ucne.keepfocus.domain.repositories

import edu.ucne.keepfocus.domain.models.FocusZone
import kotlinx.coroutines.flow.Flow

interface FocusZoneWithAppsRepository {
    suspend fun getFocusZoneWithApps(focusZoneId: Int): Flow<FocusZone>
    suspend fun getFocusZoneWithApps(): Flow<List<FocusZone>>
}