package edu.ucne.keepfocus.data.repositories

import edu.ucne.keepfocus.data.local.dao.FocusZoneWithAppsDao
import edu.ucne.keepfocus.data.mappers.toDomain
import edu.ucne.keepfocus.domain.models.FocusZoneWithApps
import edu.ucne.keepfocus.domain.repositories.FocusZoneWithAppsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FocusZoneWithAppsRepositoryImpl @Inject constructor(
    private val focusZoneWithAppsDao: FocusZoneWithAppsDao
): FocusZoneWithAppsRepository{
    override fun getFocusZoneWithApps(focusZoneId: Int): Flow<FocusZoneWithApps>? {
        return focusZoneWithAppsDao.getFocusZoneWithApps(focusZoneId)
            ?.map { focusZoneWithApp -> focusZoneWithApp.toDomain() }
    }

    override fun getFocusZoneWithApps(): Flow<List<FocusZoneWithApps>> {
        return focusZoneWithAppsDao.getFocusZoneWithApps()
            .map { focusZoneWithApps ->
                focusZoneWithApps.map { it.toDomain() }
            }
    }
}