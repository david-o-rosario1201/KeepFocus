package edu.ucne.keepfocus.domain.usecases

import edu.ucne.keepfocus.domain.models.App
import edu.ucne.keepfocus.domain.repositories.AppsRepository
import javax.inject.Inject

class GetInstalledAppsUseCase @Inject constructor(
    private val appsRepository: AppsRepository
){
    operator fun invoke(): List<App> = appsRepository.getInstalledApps()
}