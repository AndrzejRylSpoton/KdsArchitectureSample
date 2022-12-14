package com.spoton.kdsarchitecturesample.sample.domain.repository

import com.spoton.kdsarchitecturesample.common.util.answer.Answer
import com.spoton.kdsarchitecturesample.sample.domain.model.UserDomainModel

interface NetworkUsersRepository {

    suspend fun getUsers(): Answer<List<UserDomainModel>>
}