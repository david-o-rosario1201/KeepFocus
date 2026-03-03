package edu.ucne.keepfocus.domain.usecases

import edu.ucne.keepfocus.domain.models.App
import edu.ucne.keepfocus.domain.models.FocusZone
import edu.ucne.keepfocus.domain.repositories.FocusZoneRepository
import javax.inject.Inject

class UpsertFocusZoneWithApps @Inject constructor(
    private val repository: FocusZoneRepository
) {
    suspend operator fun invoke(focusZone: FocusZone, apps: List<App>){
        repository.createFocusZone(focusZone,apps)
    }
}