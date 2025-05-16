package com.musica.common.service.volley

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.ServiceResponse
import org.json.JSONObject
import java.nio.charset.Charset

/**
 * A custom Volley request class that parses network responses into [ServiceResult] objects.
 *
 * This class is used to handle HTTP requests (GET, POST, etc.), parse the JSON response,
 * and return a [ServiceResult] containing the parsed data.
 *
 * @param url The URL for the network request.
 * @param method The HTTP method (e.g., GET, POST).
 * @param errorListener Listener for handling errors during the network request.
 * @param responseListener Listener for handling successful responses.
 * @param responseType The class type of the response to be parsed.
 * @param jsonParser The JSON parser to map the response into the appropriate type.
 */
class VolleyRequest<T>(
    url: String,
    method: Int,
    errorListener: Response.ErrorListener?,
    private val responseListener: Response.Listener<ServiceResult<T>>,
    private val responseType: Class<T>,
    private val jsonParser: JsonParser,
    private val body: Any? = null,
    private val queryParameters: Map<String, String>? = null,
) : Request<ServiceResult<T>>(method, url, errorListener) {

    /**
     * Parses the network response into a [ServiceResult] object containing the parsed data.
     *
     * @param networkResponse The raw response from the network request.
     * @return A [Response] containing the [ServiceResult] if successful, or an error response if parsing fails.
     */
    public override fun parseNetworkResponse(networkResponse: NetworkResponse?): Response<ServiceResult<T>?>? {
        return try {
            // Convert the raw network response bytes into a string.
            val jsonString = networkResponse?.data?.let {
                String(it, Charset.forName(HttpHeaderParser.parseCharset(networkResponse.headers)))
            }

            // Convert the JSON string to a JSONObject, if it is not empty.
            val response = if (!jsonString.isNullOrEmpty()) JSONObject(jsonString) else null

            // Parse the JSON into the response type using the provided JSON parser.
            val parsedResponse = jsonParser.mapJsonToObject(response.toString(), responseType)

            // Create a ServiceResult object containing the parsed data and metadata about the response.
            val result = ServiceResult(
                serviceResponse = ServiceResponse(ResponseType.SUCCESS, networkResponse?.statusCode.toString()),
                parsedResponse
            )

            // Return a successful response with the parsed result and cache headers.
            Response.success(result, HttpHeaderParser.parseCacheHeaders(networkResponse))
        } catch (e: Exception) {
            // Return an error response if an exception occurs during parsing.
            Response.error(ParseError(e))
        }
    }

    public override fun getParams(): MutableMap<String, String>? {
        return queryParameters?.toMutableMap()
    }

    override fun getBody(): ByteArray? {
        return if (body == null) {
            null
        } else {
            jsonParser.mapObjectToJson(body).toByteArray()
        }
    }

    override fun getBodyContentType(): String {
        return "application/json; charset=UTF-8"
    }

    /**
     * Delivers the parsed response to the provided response listener.
     *
     * @param response The [ServiceResult] containing the parsed response.
     */
    override fun deliverResponse(response: ServiceResult<T>?) {
        responseListener.onResponse(response)
    }
}
