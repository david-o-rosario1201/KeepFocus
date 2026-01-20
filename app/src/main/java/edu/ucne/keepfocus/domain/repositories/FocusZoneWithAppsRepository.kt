package edu.ucne.keepfocus.domain.repositories

import edu.ucne.keepfocus.domain.models.FocusZone
import edu.ucne.keepfocus.domain.models.FocusZoneWithApps
import kotlinx.coroutines.flow.Flow

interface FocusZoneWithAppsRepository {
    suspend fun getFocusZoneWithApps(focusZoneId: Int): Flow<FocusZoneWithApps>?
    suspend fun getFocusZoneWithApps(): Flow<List<FocusZoneWithApps>>
}