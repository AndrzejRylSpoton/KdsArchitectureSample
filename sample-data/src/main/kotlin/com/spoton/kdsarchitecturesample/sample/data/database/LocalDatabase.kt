package com.spoton.kdsarchitecturesample.sample.data.database

import com.spoton.kdsarchitecturesample.sample.data.database.model.UserDbModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

// TODO : This is a simple local cache DB. In reality this would be a Room DB
class LocalDatabase @Inject constructor() {

    private val users: List<UserDbModel> =
        listOf(
            UserDbModel(id = "id1", name = "user1", status = "active"),
            UserDbModel(id = "id2", name = "user2", status = "inactive"),
        )

    fun observeUsers(): Flow<List<UserDbModel>> =
        flowOf(users)

    fun getUsersCount(): Int = users.size
}