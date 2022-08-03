package com.spoton.kdsarchitecturesample.sample.data.database

import com.spoton.kdsarchitecturesample.sample.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

// TODO : This is a simple local cache DB. In reality this would be a Room DB
class LocalDatabase @Inject constructor() {

    private val users: List<User> =
        listOf(
            User(id = "id1", firstName = "user", secondName = "1"),
            User(id = "id2", firstName = "user", secondName = "2"),
        )

    fun observeUsers(): Flow<List<User>> =
        flowOf(users)

    fun getUsersCount(): Int = users.size
}