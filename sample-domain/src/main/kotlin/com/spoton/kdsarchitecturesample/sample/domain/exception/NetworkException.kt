package com.spoton.kdsarchitecturesample.sample.domain.exception

data class NetworkException(
    override val message: String?,
) : Exception(message)