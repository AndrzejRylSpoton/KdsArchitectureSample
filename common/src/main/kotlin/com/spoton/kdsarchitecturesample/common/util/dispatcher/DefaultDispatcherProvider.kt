package com.spoton.kdsarchitecturesample.common.util.dispatcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.LazyThreadSafetyMode.NONE

class DefaultDispatcherProvider : DispatcherProvider {

    override val main: CoroutineDispatcher by lazy(NONE) { Dispatchers.Main.immediate }
    override val default: CoroutineDispatcher by lazy(NONE) { Dispatchers.Default }
    override val io: CoroutineDispatcher by lazy(NONE) { Dispatchers.IO }
    override val unconfined: CoroutineDispatcher by lazy(NONE) { Dispatchers.Unconfined }
}

object DispatcherProviderWrapper {

    var provider: DispatcherProvider = DefaultDispatcherProvider()
        get() {
            if (field is DefaultDispatcherProvider && isRunningTest) {
                throw IllegalStateException("Not initialized with TestDispatcherProvider")
            }
            return field
        }

    private val isRunningTest: Boolean by lazy(NONE) {
        try {
            Class.forName("org.junit.jupiter.api.Test")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }
}

@Suppress("unused")
val ViewModel.dispatcherProvider
    get() = DispatcherProviderWrapper.provider

fun ViewModel.launchInBackground(block: suspend CoroutineScope.() -> Unit): Job =
    viewModelScope.launch(dispatcherProvider.default, block = block)

fun ViewModel.launchInIO(block: suspend CoroutineScope.() -> Unit): Job =
    viewModelScope.launch(dispatcherProvider.io, block = block)

fun ViewModel.launchInMain(block: suspend CoroutineScope.() -> Unit): Job =
    viewModelScope.launch(dispatcherProvider.main, block = block)

suspend fun <T> ViewModel.onBackground(block: suspend CoroutineScope.() -> T): T =
    withContext(dispatcherProvider.default, block)

suspend fun <T> ViewModel.onIO(block: suspend CoroutineScope.() -> T): T =
    withContext(dispatcherProvider.io, block)

suspend fun <T> onMain(block: suspend CoroutineScope.() -> T): T =
    withContext(DispatcherProviderWrapper.provider.main, block)

suspend fun <T> Channel<T>.sendOnMain(element: T) {
    withContext(DispatcherProviderWrapper.provider.main) {
        send(element)
    }
}
