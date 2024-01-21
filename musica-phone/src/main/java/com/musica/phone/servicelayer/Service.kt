package com.musica.phone.servicelayer

import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.ServiceResponse
import com.musica.common.service.volley.IService
import com.musica.common.service.volley.ServiceResult
import com.musica.phone.BuildConfig
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import javax.inject.Inject
import kotlin.coroutines.resume

class Service @Inject constructor(
    private val requestQueue: RequestQueue
) : IService {

    override suspend fun <T : Any> GET(url: String, responseType: Class<T>): ServiceResult<T> =
        request(url, Method.GET, responseType)

    override suspend fun <T : Any> GET(
        url: String,
        request: Any,
        responseType: Class<T>
    ): ServiceResult<T> =
        requestWithBody(url, Method.GET, request, responseType)

    override suspend fun <T : Any> POST(url: String, responseType: Class<T>): ServiceResult<T> =
        request(url, Method.POST, responseType)

    override suspend fun <T : Any> POST(
        url: String,
        request: Any,
        responseType: Class<T>
    ): ServiceResult<T> =
        requestWithBody(url, Method.POST, request, responseType)

    override suspend fun <T : Any> PUT(
        url: String,
        request: Any,
        responseType: Class<T>
    ): ServiceResult<T> =
        requestWithBody(url, Method.PUT, request, responseType)

    override suspend fun <T : Any> PUT(url: String, responseType: Class<T>): ServiceResult<T> =
        request(url, Method.PUT, responseType)

    override suspend fun <T : Any> DELETE(url: String, responseType: Class<T>): ServiceResult<T> =
        request(url, Method.DELETE, responseType)

    override suspend fun <T : Any> DELETE(
        url: String,
        request: Any,
        responseType: Class<T>
    ): ServiceResult<T> =
        requestWithBody(url, Method.DELETE, request, responseType)

    private suspend fun <T : Any> request(
        url: String,
        method: Int,
        responseType: Class<T>
    ): ServiceResult<T> = suspendCancellableCoroutine { continuation ->
        val jsonObjectRequest = createJsonObjectRequest(
            url = url,
            method = method,
            responseType = responseType,
            continuation = continuation
        ) { response ->
            handleResponse(url, response, responseType, continuation)
        }

        sendRequest(jsonObjectRequest, continuation)
    }

    private suspend fun <T : Any> requestWithBody(
        url: String,
        method: Int,
        request: Any,
        responseType: Class<T>
    ): ServiceResult<T> = suspendCancellableCoroutine { continuation ->
        val jsonObjectRequest = createJsonObjectRequest(
            url = url,
            method = method,
            requestBody = parseDataModelToJson(request),
            responseType = responseType,
            continuation = continuation
        ) { response ->
            handleResponse(url, response, responseType, continuation)
        }

        sendRequest(jsonObjectRequest, continuation)
    }

    private fun <T : Any> handleResponse(
        url: String,
        response: JSONObject?,
        responseType: Class<T>,
        continuation: CancellableContinuation<ServiceResult<T>>
    ) {

        val data = parseJsonToDataModel(response, responseType)
        val apiResponse = ServiceResponse(ResponseType.SUCCESS, "Success: 200")
        val apiResult = ServiceResult(apiResponse, data)
        continuation.resume(apiResult)
        Log.i("Response", "url:$url \n${apiResult.serviceResponse.code} \n$data")
    }

    private fun <T : Any> createJsonObjectRequest(
        url: String,
        method: Int,
        requestBody: JSONObject? = null,
        responseType: Class<T>,
        continuation: CancellableContinuation<ServiceResult<T>>,
        onResponse: (JSONObject?) -> Unit
    ): JsonObjectRequest {
        return object : JsonObjectRequest(method, BASE_URL.format(url), requestBody,
            Response.Listener { response -> onResponse(response) },
            Response.ErrorListener { error ->
                handleError(
                    url,
                    error,
                    responseType,
                    continuation
                )
            }) {
            override fun getHeaders(): Map<String, String> = emptyMap()

            override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONObject> {
                try {
                    val jsonString = response?.data?.let {
                        String(
                            it,
                            Charset.forName(HttpHeaderParser.parseCharset(response.headers))
                        )
                    }
                    var result: JSONObject? = null
                    if (!jsonString.isNullOrEmpty()) result = JSONObject(jsonString)
                    return Response.success(
                        result,
                        HttpHeaderParser.parseCacheHeaders(response)
                    )
                } catch (e: UnsupportedEncodingException) {
                    return Response.error(ParseError(e))
                } catch (je: JSONException) {
                    return Response.error(ParseError(je))
                }
            }
        }
    }

    private fun <T : Any> handleError(
        url: String,
        error: VolleyError,
        responseType: Class<T>,
        continuation: CancellableContinuation<ServiceResult<T>>
    ) {
        val byteBody = String(
            error.networkResponse?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(error.networkResponse?.headers, "UTF-8"))
        )

        val nullData = parseJsonToDataModel(JSONObject(byteBody), responseType)
        try {
            val data = parseJsonToDataModel(
                JSONObject(byteBody),
                ExceptionResponse::class.java
            )
            val response = ServiceResponse(
                responseType = ResponseType.ERROR,
                code = data?.status.toString(),
                message = data?.message.toString()
            )
            Log.e(
                "Response",
                "url:${BASE_URL.format(url)} \ncode: ${data?.status} \nmessage: ${data?.message} \ntrace: ${error.stackTraceToString()}"
            )
            val apiResult = ServiceResult(serviceResponse = response, nullData)
            continuation.resume(apiResult)
        } catch (e: Exception) {
            Log.e("Service Exception", e.stackTraceToString())
            val apiResponse = ServiceResponse(
                responseType = ResponseType.CONNECTION_ERROR,
                code = "Connection Error",
                message = byteBody
            )
            Log.e(
                "Response",
                "url:${BASE_URL.format(url)} \ncode: ${error.networkResponse.statusCode} \nmessage: $byteBody \ntrace: ${error.stackTraceToString()}"
            )
            val apiResult = ServiceResult(serviceResponse = apiResponse, nullData)
            continuation.resume(apiResult)
        }
        // Handle error and log accordingly
        Log.e("Response", "url:$url  \nmessage: $byteBody \ntrace: ${error.stackTraceToString()}")
    }

    private fun <T : Any> sendRequest(
        jsonObjectRequest: JsonObjectRequest,
        continuation: CancellableContinuation<ServiceResult<T>>
    ) {
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(jsonObjectRequest)

        continuation.invokeOnCancellation {
            Log.i("Response Cancel", "url ${jsonObjectRequest.url}")
            jsonObjectRequest.cancel()
        }
    }

    private fun <T : Any> parseJsonToDataModel(jsonObject: JSONObject?, modelClass: Class<T>): T? {
        return if (modelClass == Void::class.java && jsonObject == null) {
            null
        } else {
            Gson().fromJson(jsonObject.toString(), modelClass)
        }
    }

    private fun parseDataModelToJson(src: Any): JSONObject = JSONObject(Gson().toJson(src))

    companion object {
        private const val BASE_URL = BuildConfig.BASE_MCA_URL
    }
}