package edu.ucne.keepfocus.data.repositories

import edu.ucne.keepfocus.data.local.dao.FocusZoneDao
import edu.ucne.keepfocus.data.mappers.asEntity
import edu.ucne.keepfocus.data.mappers.toDomain
import edu.ucne.keepfocus.domain.models.FocusZone
import edu.ucne.keepfocus.domain.repositories.FocusZoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FocusZoneRepositoryImpl @Inject constructor(
    private val focusZoneDao: FocusZoneDao
): FocusZoneRepository {
    override suspend fun upsertFocusZone(focusZone: FocusZone) {
        return focusZoneDao.upsertFocusZone(focusZone.asEntity())
    }

    override suspend fun getFocusZoneById(focusZoneId: Int): Flow<FocusZone>? {
        return focusZoneDao.getFocusZoneById(focusZoneId)
            ?.map { focusZone -> focusZone.toDomain() }
    }

    override suspend fun deleteFocusZone(focusZone: FocusZone) {
        return focusZoneDao.deleteFocusZone(focusZone.asEntity())
    }

    override suspend fun getFocusZones(): Flow<List<FocusZone>> {
        return focusZoneDao.getFocusZones()
            .map { focusZones ->
                focusZones.map { it.toDomain() }
            }
    }
}