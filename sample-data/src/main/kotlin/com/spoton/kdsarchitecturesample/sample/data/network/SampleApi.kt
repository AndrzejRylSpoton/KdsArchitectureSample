package com.spoton.kdsarchitecturesample.sample.data.network

import com.spoton.kdsarchitecturesample.sample.data.network.model.UserNetworkDto
import retrofit2.http.GET

internal interface SampleApi {

    @GET("public/v2/users")
    suspend fun getUsers(): List<UserNetworkDto>
}