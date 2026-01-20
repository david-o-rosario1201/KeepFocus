package edu.ucne.keepfocus.domain.usecases

import edu.ucne.keepfocus.data.repositories.FocusZoneRepositoryImpl
import edu.ucne.keepfocus.domain.models.FocusZone
import javax.inject.Inject

class CreateFocusZone @Inject constructor(
    private val repository: FocusZoneRepositoryImpl
) {
    suspend operator fun invoke(focusZone: FocusZone){
        repository.upsertFocusZone(focusZone)
    }
}