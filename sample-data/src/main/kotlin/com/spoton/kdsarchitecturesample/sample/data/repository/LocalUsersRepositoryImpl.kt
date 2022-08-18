package com.spoton.kdsarchitecturesample.sample.data.repository

import com.spoton.kdsarchitecturesample.common.util.answer.Answer
import com.spoton.kdsarchitecturesample.sample.data.database.LocalDatabase
import com.spoton.kdsarchitecturesample.sample.domain.model.UserDomainModel
import com.spoton.kdsarchitecturesample.sample.domain.repository.LocalUsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUsersRepositoryImpl @Inject constructor(
    private val database: LocalDatabase,
) : LocalUsersRepository {

    override suspend fun getUsersCount(): Answer<Int> =
        Answer.success(database.getUsersCount())

    override suspend fun observeUsers(): Flow<List<UserDomainModel>> =
        database.observeUsers().map {
            it.map { userDbModel ->
                UserDomainModel(
                    id = userDbModel.id,
                    name = userDbModel.name,
                    status = userDbModel.status,
                )
            }
        }
}