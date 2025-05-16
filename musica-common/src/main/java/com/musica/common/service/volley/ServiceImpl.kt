package com.musica.common.service.volley

import androidx.annotation.VisibleForTesting
import com.android.volley.ParseError
import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.toolbox.RequestFuture
import javax.inject.Inject

class ServiceImpl @Inject constructor(
    private val requestQueue: RequestQueue,
    private val jsonParser: JsonParser,
    private val errorHandler: ErrorHandler
) : IService {

    override suspend fun <T : Any> GET(
        url: String,
        responseType: Class<T>,
        queryParameters: HashMap<String, String>?
    ): ServiceResult<T> = serviceRequest(
        url = url,
        method = Method.GET,
        queryParameters = queryParameters,
        responseType = responseType
    )

    override suspend fun <T : Any> GET(
        url: String,
        request: Any,
        responseType: Class<T>,
        queryParameters: HashMap<String, String>?
    ): ServiceResult<T> = serviceRequest(
        url = url,
        method = Method.GET,
        queryParameters = queryParameters,
        body = request,
        responseType = responseType
    )

    override suspend fun <T : Any> POST(
        url: String,
        responseType: Class<T>,
        queryParameters: HashMap<String, String>?
    ): ServiceResult<T> = serviceRequest(
        url = url,
        method = Method.POST,
        queryParameters = queryParameters,
        responseType = responseType
    )

    override suspend fun <T : Any> POST(
        url: String,
        request: Any,
        responseType: Class<T>,
        queryParameters: HashMap<String, String>?
    ): ServiceResult<T> = serviceRequest(
        url = url,
        method = Method.POST,
        queryParameters = queryParameters,
        body = request,
        responseType = responseType
    )

    override suspend fun <T : Any> PUT(
        url: String,
        request: Any,
        responseType: Class<T>,
        queryParameters: HashMap<String, String>?
    ): ServiceResult<T> = serviceRequest(
        url = url,
        method = Method.PUT,
        queryParameters = queryParameters,
        body = request,
        responseType = responseType
    )

    override suspend fun <T : Any> PUT(
        url: String,
        responseType: Class<T>,
        queryParameters: HashMap<String, String>?
    ): ServiceResult<T> = serviceRequest(
        url = url,
        method = Method.PUT,
        queryParameters = queryParameters,
        responseType = responseType
    )

    override suspend fun <T : Any> DELETE(
        url: String,
        responseType: Class<T>,
        queryParameters: HashMap<String, String>?
    ): ServiceResult<T> = serviceRequest(
        url = url,
        method = Method.DELETE,
        queryParameters = queryParameters,
        responseType = responseType
    )

    override suspend fun <T : Any> DELETE(
        url: String,
        request: Any,
        responseType: Class<T>,
        queryParameters: HashMap<String, String>?
    ): ServiceResult<T> = serviceRequest(
        url = url,
        method = Method.DELETE,
        queryParameters = queryParameters,
        body = request,
        responseType = responseType
    )

    private fun <T : Any> serviceRequest(
        url: String,
        method: Int,
        queryParameters: HashMap<String, String>? = null,
        body: Any? = null,

        responseType: Class<T>
    ): ServiceResult<T> {

        //Request Path
        val path = "$BASE_URL/$url"

        // Create a future for the request result
        val future = createRequestFuture<T>()

        // Add the request to the Volley queue with the necessary configurations
        requestQueue.add(
            VolleyRequest(
                url = path,
                method = method,
                errorListener = future,
                responseListener = future,
                responseType = responseType,
                jsonParser = jsonParser,
                body = body,
                queryParameters = queryParameters
            )
        )

        return try {
            // Wait for the response to come back and return the result
            future.get()
        } catch (e: Exception) {
            // Handle any error during the request (e.g., network failure) and return the result
            errorHandler.handle<T>(ParseError(e))
        }
    }

    @VisibleForTesting
    internal fun <T : Any?> createRequestFuture(): RequestFuture<ServiceResult<T>> {
        return RequestFuture.newFuture()
    }

    companion object {
//        private const val BASE_URL = "http://156.155.253.224:8080"
        private const val BASE_URL = "http://10.0.2.2:8080"
    }
}