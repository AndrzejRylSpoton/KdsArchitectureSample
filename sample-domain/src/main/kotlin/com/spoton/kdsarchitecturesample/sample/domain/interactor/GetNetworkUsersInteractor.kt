package com.spoton.kdsarchitecturesample.sample.domain.interactor

import com.spoton.kdsarchitecturesample.common.util.answer.Answer
import com.spoton.kdsarchitecturesample.sample.domain.model.User
import com.spoton.kdsarchitecturesample.sample.domain.repository.NetworkUsersRepository
import javax.inject.Inject

class GetNetworkUsersInteractor @Inject constructor(
    private val networkUsersRepository: NetworkUsersRepository,
) {

    suspend fun run(): Answer<List<User>> =
        networkUsersRepository.getUsers()
}