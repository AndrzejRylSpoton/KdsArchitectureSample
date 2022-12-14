package com.spoton.kdsarchitecturesample.sample.data.repository

import com.spoton.kdsarchitecturesample.common.util.answer.Answer
import com.spoton.kdsarchitecturesample.sample.data.network.SampleApi
import com.spoton.kdsarchitecturesample.sample.data.network.util.WrapNetworkAnswer
import com.spoton.kdsarchitecturesample.sample.domain.model.UserDomainModel
import com.spoton.kdsarchitecturesample.sample.domain.repository.NetworkUsersRepository
import javax.inject.Inject

internal class NetworkUsersRepositoryImpl @Inject constructor(
    private val sampleApi: SampleApi,
    private val wrapNetworkAnswer: WrapNetworkAnswer,
) : NetworkUsersRepository {

    override suspend fun getUsers(): Answer<List<UserDomainModel>> =
        wrapNetworkAnswer {
            sampleApi.getUsers().map {
                UserDomainModel(
                    id = it.userId.toString(),
                    name = it.name,
                    status = it.status,
                )
            }
        }
}