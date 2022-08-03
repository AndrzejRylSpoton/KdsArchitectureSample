package com.spoton.kdsarchitecturesample.sample.data.di

import com.spoton.kdsarchitecturesample.sample.data.repository.LocalUsersRepositoryImpl
import com.spoton.kdsarchitecturesample.sample.data.repository.NetworkUsersRepositoryImpl
import com.spoton.kdsarchitecturesample.sample.domain.repository.LocalUsersRepository
import com.spoton.kdsarchitecturesample.sample.domain.repository.NetworkUsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindLocalUsersRepository(
        localUsersRepositoryImpl: LocalUsersRepositoryImpl
    ): LocalUsersRepository

    @Binds
    abstract fun bindNetworkUsersRepository(
        networkUsersRepositoryImpl: NetworkUsersRepositoryImpl
    ): NetworkUsersRepository
}