@file:Suppress(
    "UNCHECKED_CAST",
    "RedundantVisibilityModifier",
    "MemberVisibilityCanBePrivate",
    "unused",
    "TooGenericExceptionCaught"
)

package com.spoton.kdsarchitecturesample.common.util.answer

import com.spoton.kdsarchitecturesample.common.util.answer.Answer.Companion.failure
import com.spoton.kdsarchitecturesample.common.util.answer.Answer.Companion.success

/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

typealias AnswerNothing = Answer<NoAnswer>

object NoAnswer

/**
 * Scrapped from {@link kotlin.Result } which is inline class and don't work with mockito
 * TODO update it each kotlin version, that's from 1.4.30
 */
public class Answer<out T> @PublishedApi internal constructor(
    @PublishedApi
    internal val value: Any?
) {
    // discovery

    /**
     * Returns `true` if this instance represents a successful outcome.
     * In this case [isFailure] returns `false`.
     */
    public val isSuccess: Boolean get() = value !is Failure

    /**
     * Returns `true` if this instance represents a failed outcome.
     * In this case [isSuccess] returns `false`.
     */
    public val isFailure: Boolean get() = value is Failure

    // value & exception retrieval

    /**
     * Returns the encapsulated value if this instance represents [success][isSuccess] or `null`
     * if it is [failure][isFailure].
     *
     * This function is a shorthand for `getOrElse { null }` (see [getOrElse]) or
     * `fold(onSuccess = { it }, onFailure = { null })` (see [fold]).
     */

    public fun getOrNull(): T? =
        when {
            isFailure -> null
            else -> value as T
        }

    /**
     * Returns the encapsulated [Throwable] exception if this instance represents [failure][isFailure] or `null`
     * if it is [success][isSuccess].
     *
     * This function is a shorthand for `fold(onSuccess = { null }, onFailure = { it })` (see [fold]).
     */
    public fun exceptionOrNull(): Throwable? =
        when (value) {
            is Failure -> value.exception
            else -> null
        }

    /**
     * Returns a string `Success(v)` if this instance represents [success][isSuccess]
     * where `v` is a string representation of the value or a string `Failure(x)` if
     * it is [failure][isFailure] where `x` is a string representation of the exception.
     */
    public override fun toString(): String =
        when (value) {
            is Failure -> value.toString() // "Failure($exception)"
            else -> "Success($value)"
        }

    // companion with constructors

    /**
     * Companion object for [Answer] class that contains its constructor functions
     * [success] and [failure].
     */
    public companion object {
        /**
         * Returns an instance that encapsulates the given [value] as successful value.
         */
        @Suppress("INAPPLICABLE_JVM_NAME")

        @JvmName("success")
        public fun <T> success(value: T): Answer<T> =
            Answer(value)

        /**
         * Returns an instance that encapsulates the given [Throwable] [exception] as failure.
         */
        @Suppress("INAPPLICABLE_JVM_NAME")

        @JvmName("failure")
        public fun <T> failure(exception: Throwable): Answer<T> =
            Answer(createFailure(exception))

        @JvmName("nothing")
        public fun nothing(): AnswerNothing =
            success(NoAnswer)
    }

    internal class Failure(
        @JvmField
        val exception: Throwable
    ) {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception)"
    }

    /**
     * Returns the encapsulated value if this instance represents [success][isSuccess] or throws the encapsulated [Throwable] exception
     * if it is [failure][isFailure].
     *
     * This function is a shorthand for `getOrElse { throw it }` (see [getOrElse]).
     */
    public fun getOrThrow(): T {
        throwOnFailure()
        return value as T
    }

    /**
     * Returns the encapsulated value if this instance represents [success][isSuccess] or the
     * result of [onFailure] function for the encapsulated [Throwable] exception if it is [failure][isFailure].
     *
     * Note, that this function rethrows any [Throwable] exception thrown by [onFailure] function.
     *
     * This function is a shorthand for `fold(onSuccess = { it }, onFailure = onFailure)` (see [fold]).
     */
    public inline fun <R, T : R> Answer<T>.getOrElse(onFailure: (exception: Throwable) -> R): R {
        return when (val exception = exceptionOrNull()) {
            null -> value as T
            else -> onFailure(exception)
        }
    }

    /**
     * Returns the encapsulated value if this instance represents [success][isSuccess] or the
     * [defaultValue] if it is [failure][isFailure].
     *
     * This function is a shorthand for `getOrElse { defaultValue }` (see [getOrElse]).
     */
    public fun <R, T : R> Answer<T>.getOrDefault(defaultValue: R): R {
        if (isFailure) return defaultValue
        return value as T
    }

    /**
     * Returns the result of [onSuccess] for the encapsulated value if this instance represents [success][isSuccess]
     * or the result of [onFailure] function for the encapsulated [Throwable] exception if it is [failure][isFailure].
     *
     * Note, that this function rethrows any [Throwable] exception thrown by [onSuccess] or by [onFailure] function.
     */
    public inline fun <R> fold(
        onSuccess: (value: T) -> R,
        onFailure: (exception: Throwable) -> R
    ): R {
        return when (val exception = exceptionOrNull()) {
            null -> onSuccess(value as T)
            else -> onFailure(exception)
        }
    }

// transformation

    /**
     * Returns the encapsulated result of the given [transform] function applied to the encapsulated value
     * if this instance represents [success][isSuccess] or the
     * original encapsulated [Throwable] exception if it is [failure][isFailure].
     *
     * Note, that this function rethrows any [Throwable] exception thrown by [transform] function.
     * See [mapCatching] for an alternative that encapsulates exceptions.
     */
    public inline fun <R> map(transform: (value: T) -> R): Answer<R> {
        return when {
            isSuccess -> success(transform(value as T).checkResult())
            else -> Answer(value)
        }
    }

    fun <T> T.checkResult() =
        if (this is Answer<*>) {
            throw IllegalStateException("use `then` or AnswerNothing")
        } else {
            this
        }

    /**
     * Returns the encapsulated result of the given [transform] function applied to the encapsulated value
     * if this instance represents [success][isSuccess] or the
     * original encapsulated [Throwable] exception if it is [failure][isFailure].
     *
     * This function catches any [Throwable] exception thrown by [transform] function and encapsulates it as a failure.
     * See [map] for an alternative that rethrows exceptions from `transform` function.
     */
    public inline fun <R> mapCatching(transform: (value: T) -> R): Answer<R> {
        return when {
            isSuccess -> runCatching { transform(value as T) }
            else -> Answer(value)
        }
    }

    public inline fun <R> then(transform: (value: T) -> Answer<R>): Answer<R> {
        return when {
            isSuccess -> transform(value as T)
            else -> Answer(value)
        }
    }

    public inline fun <R, X : Throwable> recoverFrom(
        classType: Class<X>,
        transform: (exception: X) -> Answer<R>
    ): Answer<R> {
        val exception = exceptionOrNull()
        return when {
            exception == null -> Answer(value)
            classType.isInstance(exception) -> transform(exception as X)
            else -> failure(exception)
        }
    }

    /**
     * Returns the encapsulated result of the given [transform] function applied to the encapsulated [Throwable] exception
     * if this instance represents [failure][isFailure] or the
     * original encapsulated value if it is [success][isSuccess].
     *
     * Note, that this function rethrows any [Throwable] exception thrown by [transform] function.
     * See [recoverCatching] for an alternative that encapsulates exceptions.
     */
    public inline fun <R, T : R> Answer<T>.recover(transform: (exception: Throwable) -> R): Answer<R> {
        return when (val exception = exceptionOrNull()) {
            null -> this
            else -> success(transform(exception))
        }
    }

    /**
     * Returns the encapsulated result of the given [transform] function applied to the encapsulated [Throwable] exception
     * if this instance represents [failure][isFailure] or the
     * original encapsulated value if it is [success][isSuccess].
     *
     * This function catches any [Throwable] exception thrown by [transform] function and encapsulates it as a failure.
     * See [recover] for an alternative that rethrows exceptions.
     */
    public inline fun <R, T : R> Answer<T>.recoverCatching(transform: (exception: Throwable) -> R): Answer<R> {
        return when (val exception = exceptionOrNull()) {
            null -> this
            else -> runCatching { transform(exception) }
        }
    }

// "peek" onto value/exception and pipe

    /**
     * Performs the given [action] on the encapsulated [Throwable] exception if this instance represents [failure][isFailure].
     * Returns the original `Result` unchanged.
     */
    public inline fun onFailure(action: (exception: Throwable) -> Unit): Answer<T> {
        exceptionOrNull()?.let { action(it) }
        return this
    }

    /**
     * Performs the given [action] on the encapsulated value if this instance represents [success][isSuccess].
     * Returns the original `Result` unchanged.
     */
    public inline fun onSuccess(action: (value: T) -> Unit): Answer<T> {
        if (isSuccess) action(value as T)
        return this
    }
}

/**
 * Creates an instance of internal marker [Failure] class to
 * make sure that this class is not exposed in ABI.
 */
@PublishedApi
internal fun createFailure(exception: Throwable): Any =
    Answer.Failure(exception)

/**
 * Throws exception if the result is failure. This internal function minimizes
 * inlined bytecode for [getOrThrow] and makes sure that in the future we can
 * add some exception-augmenting logic here (if needed).
 */
@PublishedApi
internal fun Answer<*>.throwOnFailure() {
    if (value is Answer.Failure) throw value.exception
}

/**
 * Calls the specified function [block] and returns its encapsulated result if invocation was successful,
 * catching any [Throwable] exception that was thrown from the [block] function execution and encapsulating it as a failure.
 */
public inline fun <R> runCatching(block: () -> R): Answer<R> {
    return try {
        success(block())
    } catch (e: Throwable) {
        failure(e)
    }
}

/**
 * Calls the specified function [block] with `this` value as its receiver and returns its encapsulated result if invocation was successful,
 * catching any [Throwable] exception that was thrown from the [block] function execution and encapsulating it as a failure.
 */
public inline fun <T, R> T.runCatching(block: T.() -> R): Answer<R> {
    return try {
        success(block())
    } catch (e: Throwable) {
        failure(e)
    }
}