package com.spoton.kdsarchitecturesample.common.di

import android.content.Context
import com.spoton.kdsarchitecturesample.common.resourceprovider.ResourceProvider
import com.spoton.kdsarchitecturesample.common.resourceprovider.ResourceProviderImpl
import com.spoton.kdsarchitecturesample.common.ui.presenter.toast.ToastPresenter
import com.spoton.kdsarchitecturesample.common.ui.presenter.toast.ToastPresenterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider =
        ResourceProviderImpl(context)

    @Provides
    fun provideToastPresenter(@ApplicationContext context: Context): ToastPresenter =
        ToastPresenterImpl(context)
}