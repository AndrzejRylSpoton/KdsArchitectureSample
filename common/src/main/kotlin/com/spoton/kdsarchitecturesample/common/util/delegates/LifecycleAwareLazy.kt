package com.spoton.kdsarchitecturesample.common.util.delegates

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.io.Closeable

private class LifecycleAwareLazy<out T>(
    private val lifecycleProvider: () -> Lifecycle,
    private val provideValue: () -> T
) : Lazy<T>, LifecycleEventObserver {

    private var cachedValue: T? = null

    override val value: T
        get() {
            if (cachedValue == null) {
                val lifecycle = lifecycleProvider()

                if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
                    lifecycle.addObserver(this)
                    cacheValue()
                }
            }

            return cachedValue ?: provideValue() // Don't cache for destroyed state.
        }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) resetValue()
    }

    override fun isInitialized(): Boolean = cachedValue != null

    private fun cacheValue() {
        cachedValue = provideValue()
    }

    private fun resetValue() {
        invokePotentialClosable()
        cachedValue = null
        lifecycleProvider().removeObserver(this)
    }

    private fun invokePotentialClosable() =
        (cachedValue as? Closeable)?.close()
}

fun <T> Fragment.lazyViewLifecycle(initializer: () -> T): Lazy<T> =
    LifecycleAwareLazy({ viewLifecycleOwner.lifecycle }, initializer)

fun <T> AppCompatActivity.lazyViewLifecycle(initializer: () -> T): Lazy<T> =
    LifecycleAwareLazy({ lifecycle }, initializer)
