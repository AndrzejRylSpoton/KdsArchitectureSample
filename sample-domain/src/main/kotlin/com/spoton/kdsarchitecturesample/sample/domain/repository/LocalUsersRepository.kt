package com.spoton.kdsarchitecturesample.sample.domain.repository

import com.spoton.kdsarchitecturesample.common.util.answer.Answer
import com.spoton.kdsarchitecturesample.sample.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LocalUsersRepository {

    suspend fun getUsersCount(): Answer<Int>

    suspend fun observeUsers(): Flow<List<User>>
}