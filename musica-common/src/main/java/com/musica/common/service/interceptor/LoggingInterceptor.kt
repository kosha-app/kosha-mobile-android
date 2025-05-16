package com.musica.common.service.interceptor

import com.musica.common.logging.Logger
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import org.json.JSONObject
import java.util.concurrent.atomic.AtomicLong

private const val TAG = "LoggingInterceptor"

internal class LoggingInterceptor: Interceptor {

    private val requestCount = AtomicLong()
    private val logger = Logger()

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestId = requestCount.getAndIncrement()
        val request = chain.request().also {
            logRequest(it, requestId)
        }
        val startTime = System.currentTimeMillis()
        return chain.proceed(request).also {
            logResponse(it, requestId, System.currentTimeMillis() - startTime)
        }
    }

    private fun logRequest(request: Request, requestId: Long) {
        val output = StringBuilder()
        output.appendLine(">>>>> Request #$requestId >>>>>")
        output.appendLine("Request to ${request.method}: ${request.url}")

        request.headers.toMultimap().toSortedMap().forEach { (key, value) ->
            output.appendLine("Request: $key - ${value.joinToString()}")
        }

        request.body?.let { body ->
            val buffer = Buffer().apply { body.writeTo(this) }
            val charset = body.contentType()?.charset() ?: Charsets.UTF_8
            output.appendLine("Request body (${formatSize(buffer.size)}):")
            output.appendLine(buffer.readString(charset).tryFormatJson())
        }

        output.append(">>>>> End request #$requestId >>>>>")
        logger.i(TAG ,output.toString())
    }

    private fun logResponse(response: Response, requestId: Long, durationMillis: Long) {
        val output = StringBuilder()

        output.appendLine("<<<<< Response to request #$requestId <<<<<")
        output.appendLine("Response to ${response.request.method}: ${response.request.url}")
        output.appendLine("Response status: ${response.code} (took ${formatDuration(durationMillis)})")

        response.headers.toMultimap().toSortedMap().forEach { (key, value) ->
            output.appendLine("Response: $key - ${value.joinToString()}")
        }

        response.body?.let { body ->
            val source = body.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer.clone()
            val charset = body.contentType()?.charset() ?: Charsets.UTF_8

            output.appendLine("Response body (${formatSize(buffer.size)}):")
            output.appendLine(buffer.readString(charset).tryFormatJson())
        }

        output.append("<<<<< End response to request #$requestId <<<<<")
        logger.i(TAG ,output.toString())
    }

    private fun formatSize(bytes: Long): String {
        return if (bytes < 1024) {
            "$bytes bytes"
        } else {
            "%.2f kilobytes".format(bytes / 1024.0)
        }
    }

    private fun formatDuration(milliseconds: Long): String {
        return if (milliseconds < 1000) {
            "$milliseconds milliseconds"
        } else {
            "%.2f seconds".format(milliseconds / 1000.0)
        }
    }

    private fun String.tryFormatJson(): String {
        return kotlin.runCatching {
            JSONObject(this).toString(2)
        }.getOrElse { this }
    }
}