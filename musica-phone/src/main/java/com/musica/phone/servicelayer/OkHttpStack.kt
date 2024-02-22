package com.musica.phone.servicelayer

import com.android.volley.Header
import com.android.volley.toolbox.BaseHttpStack
import com.android.volley.toolbox.HttpResponse
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

internal class OkHttpStack(
    private val client: OkHttpClient,
) : BaseHttpStack() {

    private val requestBuilderProvider: () -> Request.Builder = { Request.Builder() }

    override fun executeRequest(request: com.android.volley.Request<*>, additionalHeaders: Map<String, String>): HttpResponse {
        val okHttpRequest = createOkHttpRequest(request, additionalHeaders)
        val okHttpResponse = client.newCall(okHttpRequest).execute()
        val body = okHttpResponse.body
        val contentLength = body?.contentLength()?.toInt() ?: 0
        val responseHeaders = mapToVolleyHeaders(okHttpResponse.headers)
        return HttpResponse(okHttpResponse.code, responseHeaders, contentLength, body?.byteStream())
    }

    private fun createOkHttpRequest(request: com.android.volley.Request<*>, additionalHeaders: Map<String, String>): Request {
        val headers = request.headers
        return requestBuilderProvider.invoke()
            .url(request.url)
            .addHeaders(headers + additionalHeaders)
            .setConnectionParametersForRequest(request)
            .build()
    }

    private fun Request.Builder.addHeaders(headers: Map<String, String>): Request.Builder {
        headers.forEach {
            addHeader(it.key, it.value)
        }
        return this
    }

    private fun Request.Builder.setConnectionParametersForRequest(request: com.android.volley.Request<*>): Request.Builder {
        return when (request.method) {
            com.android.volley.Request.Method.DEPRECATED_GET_OR_POST -> {
                val postBody = request.body
                if (postBody != null) {
                    post(postBody.toRequestBody(request.bodyContentType.toMediaTypeOrNull()))
                } else {
                    get()
                }
            }
            com.android.volley.Request.Method.GET -> get()
            com.android.volley.Request.Method.DELETE -> delete(createRequestBody(request))
            com.android.volley.Request.Method.POST -> post(createRequestBody(request))
            com.android.volley.Request.Method.PUT -> put(createRequestBody(request))
            com.android.volley.Request.Method.HEAD -> head()
            com.android.volley.Request.Method.OPTIONS -> method("OPTIONS", null)
            com.android.volley.Request.Method.TRACE -> method("TRACE", null)
            com.android.volley.Request.Method.PATCH -> patch(createRequestBody(request))
            else -> throw IllegalStateException("Unknown method type.")
        }
    }

    private fun createRequestBody(r: com.android.volley.Request<*>): RequestBody {
        val body = r.body ?: return ByteArray(0).toRequestBody()
        return body.toRequestBody(r.bodyContentType.toMediaTypeOrNull())
    }

    private fun mapToVolleyHeaders(responseHeaders: Headers): List<Header> {
        return responseHeaders.toMultimap().map {
            Header(it.key, it.value.joinToString())
        }
    }

}