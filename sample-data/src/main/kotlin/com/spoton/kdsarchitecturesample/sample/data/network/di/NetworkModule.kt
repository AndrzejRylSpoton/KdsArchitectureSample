package com.spoton.kdsarchitecturesample.sample.data.network.di

import com.spoton.kdsarchitecturesample.sample.data.network.SampleApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder().build()

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    @Provides
    fun provideSampleApi(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): SampleApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://gorest.co.in/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SampleApi::class.java)
}