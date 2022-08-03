package com.spoton.kdsarchitecturesample.sample.data.repository

import com.spoton.kdsarchitecturesample.common.util.answer.Answer
import com.spoton.kdsarchitecturesample.sample.domain.model.User
import com.spoton.kdsarchitecturesample.sample.domain.repository.NetworkUsersRepository
import javax.inject.Inject

class NetworkUsersRepositoryImpl @Inject constructor() : NetworkUsersRepository {

    override suspend fun getUsers(): Answer<List<User>> {
        TODO("Not yet implemented")
    }
}