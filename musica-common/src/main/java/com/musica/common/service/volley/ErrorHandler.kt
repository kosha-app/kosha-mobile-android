package com.musica.common.service.volley

import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.musica.common.service.models.response.ExceptionResponse
import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.ServiceResponse
import java.nio.charset.Charset
import javax.inject.Inject

/**
 * Handles errors returned by the Volley request.
 */
interface ErrorHandler {
    fun <T : Any> handle(error: VolleyError): ServiceResult<T>
}

/**
 * Default implementation of [ErrorHandler] that parses error bodies.
 */
class ErrorHandlerImpl @Inject constructor(
    private val jsonParser: JsonParser
) : ErrorHandler {
    override fun <T : Any> handle(error: VolleyError): ServiceResult<T> {
        return try {
            val jsonString = String(
                error.networkResponse?.data ?: ByteArray(0),
                Charset.forName(
                    HttpHeaderParser.parseCharset(
                        error.networkResponse?.headers,
                        "UTF-8"
                    )
                )
            )
            val exceptionResponse = jsonParser.mapJsonToObject(jsonString, ExceptionResponse::class.java)
            ServiceResult(
                ServiceResponse(
                    responseType = ResponseType.ERROR,
                    code = exceptionResponse?.status.toString(),
                    message = exceptionResponse?.message.orEmpty()
                )
            )
        } catch (_: Exception) {
            ServiceResult(
                ServiceResponse(
                    responseType = ResponseType.CONNECTION_ERROR,
                    code = "Connection Error",
                    message = "Something went wrong"
                )
            )
        }
    }
}
