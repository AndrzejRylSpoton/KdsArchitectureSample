package com.spoton.kdsarchitecturesample.sample.domain.interactor

import com.spoton.kdsarchitecturesample.sample.domain.model.UserDomainModel
import com.spoton.kdsarchitecturesample.sample.domain.repository.LocalUsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveLocalUsersInteractor @Inject constructor(
    private val localUsersRepository: LocalUsersRepository,
) {

    suspend fun run(): Flow<List<UserDomainModel>> =
        localUsersRepository.observeUsers()
}