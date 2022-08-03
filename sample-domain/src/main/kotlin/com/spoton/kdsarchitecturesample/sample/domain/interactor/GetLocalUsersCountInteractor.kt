package com.spoton.kdsarchitecturesample.sample.domain.interactor

import com.spoton.kdsarchitecturesample.common.util.answer.Answer
import com.spoton.kdsarchitecturesample.sample.domain.repository.LocalUsersRepository
import javax.inject.Inject

class GetLocalUsersCountInteractor @Inject constructor(
    private val localUsersRepository: LocalUsersRepository,
) {

    suspend fun run(): Answer<Int> =
        localUsersRepository.getUsersCount()
}