package com.musica.phone.servicelayer

import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.musica.common.service.models.response.DefaultResponse
import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.ServiceResponse
import com.musica.common.service.volley.IService
import com.musica.common.service.volley.ServiceException
import com.musica.common.service.volley.ServiceResult
import com.musica.phone.BuildConfig
import com.musica.phone.StarterApplication
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import java.nio.charset.Charset
import kotlin.coroutines.resume


class Service : IService {

    private var requestQueue: RequestQueue = StarterApplication.getRequestQueue()


    @kotlin.jvm.Throws(ServiceException::class)
    override suspend fun <T : Any> GET(
        url: String, responseType: Class<T>
    ): ServiceResult<T> {
        return request(url = url, method = Method.GET, responseType = responseType)
    }

    @kotlin.jvm.Throws(ServiceException::class)
    override suspend fun <T : Any> GET(
        url: String, request: Any, responseType: Class<T>
    ): ServiceResult<T> {
        return requestWithBody(
            url = url, method = Method.GET, responseType = responseType, request = request
        )
    }

    @kotlin.jvm.Throws(ServiceException::class)
    override suspend fun <T : Any> POST(
        url: String, responseType: Class<T>
    ): ServiceResult<T> {
        return request(url = url, method = Method.POST, responseType = responseType)
    }

    @kotlin.jvm.Throws(ServiceException::class)
    override suspend fun <T : Any> POST(
        url: String, request: Any, responseType: Class<T>
    ): ServiceResult<T> {
        return requestWithBody(
            url = url, method = Method.POST, responseType = responseType, request = request
        )
    }

    @kotlin.jvm.Throws(ServiceException::class)
    override suspend fun <T : Any> PUT(
        url: String, request: Any, responseType: Class<T>
    ): ServiceResult<T> {
        return requestWithBody(
            url = url, method = Method.PUT, responseType = responseType, request = request
        )
    }

    @kotlin.jvm.Throws(ServiceException::class)
    override suspend fun <T : Any> PUT(
        url: String, responseType: Class<T>
    ): ServiceResult<T> {
        return request(url = url, method = Method.PUT, responseType = responseType)
    }

    @kotlin.jvm.Throws(ServiceException::class)
    override suspend fun <T : Any> DELETE(url: String, responseType: Class<T>): ServiceResult<T> {
        return request(url = url, method = Method.DELETE, responseType = responseType)
    }

    @kotlin.jvm.Throws(ServiceException::class)
    override suspend fun <T : Any> DELETE(
        url: String, request: Any, responseType: Class<T>
    ): ServiceResult<T> {
        return requestWithBody(
            url = url, method = Method.DELETE, responseType = responseType, request = request
        )
    }

    @kotlin.jvm.Throws(ServiceException::class)
    private suspend fun <T : Any> request(
        url: String,
        method: Int,
        responseType: Class<T>,
    ): ServiceResult<T> {
        val headers: Map<String, String>? = null
        return suspendCancellableCoroutine { continuation ->
            val jsonObjectRequest =
                object : JsonObjectRequest(method,  BASE_URL.format(url), null, Response.Listener { response ->

                    val data = parseJsonToDataModel(response, responseType)
                    val apiResponse =
                        ServiceResponse(responseType = ResponseType.SUCCESS, code = "Success: 200")
                    val apiResult: ServiceResult<T> =
                        ServiceResult(data = data, serviceResponse = apiResponse)
                    continuation.resume(apiResult)
                    Log.i("Response", "url:$url \n${apiResult.serviceResponse.code} \n${response}")
                }, Response.ErrorListener { error ->
                    val byteBody = String(
                        error.networkResponse?.data ?: ByteArray(0), Charset.forName(
                            HttpHeaderParser.parseCharset(headers, "UTF-8")
                        )
                    )
                    val apiResult = if (byteBody.isBlank() || byteBody.isEmpty()) {
                        val apiResponse =
                            ServiceResponse(
                                responseType = ResponseType.CONNECTION_ERROR,
                                code = "Connection Error",
                                message = ""
                            )
                        ServiceResult(serviceResponse = apiResponse)
                    } else {

                        try {
                            val data = parseJsonToDataModel(JSONObject(byteBody), responseType)
                            val response = ServiceResponse(
                                responseType = ResponseType.ERROR,
                                code = error.networkResponse.statusCode.toString(),
                                message = byteBody
                            )
                            ServiceResult(data = data, serviceResponse = response)
                        } catch (e: Exception) {
                            Log.e("Service Exception", e.stackTraceToString())
                            val apiResponse = ServiceResponse(
                                responseType = ResponseType.CONNECTION_ERROR,
                                code = "Connection Error",
                                message = byteBody
                            )
                            ServiceResult(serviceResponse = apiResponse)
                        }
                    }
                    Log.e(
                        "Response",
                        "url:$url  \nmessage: $byteBody \ntrace: ${error.stackTraceToString()}"
                    )
                    continuation.resume(apiResult)
                }) {
                    override fun getHeaders(): Map<String, String> {
                        return headers ?: super.getHeaders()
                    }
                }


            // Sets timeout
            jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

            // Add the request to the requestQueue
            requestQueue.add(jsonObjectRequest)

            // If the coroutine is cancelled, cancel the request
            continuation.invokeOnCancellation {
                Log.i("Response Cancel", "url $url")
                jsonObjectRequest.cancel()
            }
        }
    }

    @kotlin.jvm.Throws(ServiceException::class)
    private suspend fun <T : Any> requestWithBody(
        url: String,
        method: Int,
        request: Any,
        responseType: Class<T>,
    ): ServiceResult<T> {
        val headers: Map<String, String>? = null
        return suspendCancellableCoroutine { continuation ->

            val jsonObjectRequest = object : JsonObjectRequest(method,
                BASE_URL.format(url),
                parseDataModelToJson(request),
                Response.Listener { response ->
                    val data = parseJsonToDataModel(response, responseType)
                    val apiResponse = ServiceResponse(
                        responseType = ResponseType.SUCCESS, code = "Success: 200"
                    )
                    val apiResult: ServiceResult<T> =
                        ServiceResult(data = data, serviceResponse = apiResponse)
                    Log.i(
                        "Response", "url:$url \n${apiResult.serviceResponse.code} \n${response}"
                    )
                    continuation.resume(apiResult)
                },
                Response.ErrorListener { error ->
                    val byteBody = String(
                        error.networkResponse?.data ?: ByteArray(0), Charset.forName(
                            HttpHeaderParser.parseCharset(headers, "UTF-8")
                        )
                    )
                    val apiResult = if (byteBody.isBlank() || byteBody.isEmpty()) {
                        val apiResponse = ServiceResponse(
                            responseType = ResponseType.CONNECTION_ERROR,
                            code = "Connection Error",
                            message = byteBody
                        )
                        ServiceResult(serviceResponse = apiResponse)
                    } else {

                        try {
                            val data = parseJsonToDataModel(JSONObject(byteBody), responseType)
                            val response = ServiceResponse(
                                responseType = ResponseType.ERROR,
                                code = error.networkResponse.statusCode.toString(),
                                message = byteBody
                            )
                            ServiceResult(data = data, serviceResponse = response)
                        } catch (e: Exception) {
                            Log.e("Service Exception", e.stackTraceToString())
                            val apiResponse = ServiceResponse(
                                responseType = ResponseType.CONNECTION_ERROR,
                                code = "Connection Error",
                                message = byteBody
                            )
                            ServiceResult(serviceResponse = apiResponse)
                        }
                    }
                    Log.e(
                        "Response",
                        "url:$url \ncode: ${error.networkResponse.statusCode} \nmessage: $byteBody \ntrace: ${error.stackTraceToString()}"
                    )
                    continuation.resume(apiResult)
                }) {
                override fun getHeaders(): Map<String, String> {
                    return headers ?: super.getHeaders()
                }
            }

            // Sets timeout
            jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

            // Add the request to the requestQueue
            requestQueue.add(jsonObjectRequest)

            // If the coroutine is cancelled, cancel the request
            continuation.invokeOnCancellation {
                jsonObjectRequest.cancel()
            }
        }
    }


    private fun <T : Any> parseJsonToDataModel(jsonObject: JSONObject, modelClass: Class<T>): T {
        return Gson().fromJson(jsonObject.toString(), modelClass)
    }

    private fun parseDataModelToJson(src: Any): JSONObject {
        val request = Gson().toJson(src)
        return JSONObject(request)
    }

    companion object {
        private const val BASE_URL = BuildConfig.BASE_MCA_URL
    }
}
