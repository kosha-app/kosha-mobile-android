package com.musica.common.service.volley


interface IService {
    suspend fun <T : Any> GET(url: String, responseType: Class<T>, queryParameters: HashMap<String, String>? = null): ServiceResult<T>

    suspend fun <T : Any> GET( url: String, request: Any, responseType: Class<T>, queryParameters: HashMap<String, String>? = null,): ServiceResult<T>

    suspend fun <T : Any> POST(url: String, responseType: Class<T>, queryParameters: HashMap<String, String>? = null,): ServiceResult<T>

    suspend fun <T : Any> POST(url: String, request: Any, responseType: Class<T>, queryParameters: HashMap<String, String>? = null): ServiceResult<T>

    suspend fun <T : Any> PUT(url: String,responseType: Class<T>, queryParameters: HashMap<String, String>? = null,): ServiceResult<T>

    suspend fun <T : Any> PUT(url: String, request: Any, responseType: Class<T>, queryParameters: HashMap<String, String>? = null,): ServiceResult<T>

    suspend fun <T : Any> DELETE(url: String, responseType: Class<T>, queryParameters: HashMap<String, String>? = null,): ServiceResult<T>

    suspend fun <T : Any> DELETE(url: String, request: Any, responseType: Class<T>, queryParameters: HashMap<String, String>? = null,): ServiceResult<T>
}