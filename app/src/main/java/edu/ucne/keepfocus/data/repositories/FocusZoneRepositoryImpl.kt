package edu.ucne.keepfocus.data.repositories

import edu.ucne.keepfocus.data.local.dao.FocusZoneDao
import edu.ucne.keepfocus.data.mappers.asEntity
import edu.ucne.keepfocus.data.mappers.toDomain
import edu.ucne.keepfocus.domain.models.FocusZone
import edu.ucne.keepfocus.domain.models.FocusZoneWithApps
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

    override fun observeFocusZoneById(focusZoneId: Int): Flow<FocusZone?> {
        return focusZoneDao.observeFocusZoneById(focusZoneId)
            .map { it?.toDomain() }
    }

    override suspend fun deleteFocusZone(focusZone: FocusZone) {
        return focusZoneDao.deleteFocusZone(focusZone.asEntity())
    }

    override fun observeFocusZones(): Flow<List<FocusZone>> {
        return focusZoneDao.observeFocusZones()
            .map { focusZones ->
                focusZones.map { it.toDomain() }
            }
    }

    override fun observeFocusZoneWithApps(focusZoneId: Int): Flow<FocusZoneWithApps?> {
        return focusZoneDao.observeFocusZoneWithApps(focusZoneId)
            .map { it?.toDomain() }
    }

    override fun observeFocusZonesWithApps(): Flow<List<FocusZoneWithApps>> {
        return focusZoneDao.observeFocusZonesWithApps()
            .map { list -> list.map { it.toDomain() } }
    }
}