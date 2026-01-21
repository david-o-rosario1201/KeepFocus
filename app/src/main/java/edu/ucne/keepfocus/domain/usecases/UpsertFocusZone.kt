package edu.ucne.keepfocus.domain.usecases

import edu.ucne.keepfocus.domain.models.FocusZone
import edu.ucne.keepfocus.domain.repositories.FocusZoneRepository
import javax.inject.Inject

class UpsertFocusZone @Inject constructor(
    private val repository: FocusZoneRepository
) {
    suspend operator fun invoke(focusZone: FocusZone){
        repository.upsertFocusZone(focusZone)
    }
}