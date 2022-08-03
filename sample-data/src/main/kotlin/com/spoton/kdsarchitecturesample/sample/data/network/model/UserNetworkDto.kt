package com.spoton.kdsarchitecturesample.sample.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
internal data class UserNetworkDto(
    @Json(name = "id") val userId: Int,
    @Json(name = "name") val name: String,
    @Json(name = "status") val status: String,
)
