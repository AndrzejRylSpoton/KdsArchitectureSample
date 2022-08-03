package com.spoton.kdsarchitecturesample.sample.data.network.util

import com.spoton.kdsarchitecturesample.common.util.answer.Answer
import com.spoton.kdsarchitecturesample.common.util.answer.Answer.Companion.failure
import com.spoton.kdsarchitecturesample.common.util.answer.Answer.Companion.success
import com.spoton.kdsarchitecturesample.sample.domain.exception.NetworkException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import okhttp3.Response
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

internal open class WrapNetworkAnswer @Inject constructor() {

    suspend operator fun <T> invoke(block: suspend CoroutineScope.() -> T): Answer<T> =
        try {
            coroutineScope {
                success(block())
            }
        } catch (e: IOException) {
            makeClientError(e, "Connectivity Failed")
        } catch (e: HttpException) {
            val code = e.code()
            val url = e.extractRequestUrl()
            val errorMessage = e.message()

            val domainException = NetworkException(message = errorMessage)
            Timber.e(e, "HttpException: HTTP %s. URL: %s. MESSAGE: %s", code, url, errorMessage)
            failure(domainException)
        }

    private fun <T> makeClientError(e: Exception, message: String): Answer<T> {
        Timber.e(e, message)
        return failure(NetworkException(message = message))
    }

    // Cast is required because Android Studio has trouble with recognizing correct version of okHttp3.
    // Datadog is using ver3 and we're using ver4
    @Suppress("USELESS_CAST")
    private fun HttpException.extractRequestUrl() =
        (response()?.raw() as? Response?)?.request?.url?.toString()

}
