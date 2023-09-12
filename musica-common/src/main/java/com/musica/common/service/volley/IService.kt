package com.musica.common.service.volley


interface IService {
    @kotlin.jvm.Throws(ServiceException::class)
    suspend fun <T : Any> GET(url: String, responseType: Class<T>): ServiceResult<T>
    @kotlin.jvm.Throws(ServiceException::class)
    suspend fun <T : Any> GET( url: String, request: Any, responseType: Class<T>): ServiceResult<T>
    @kotlin.jvm.Throws(ServiceException::class)
    suspend fun <T : Any> POST(url: String, responseType: Class<T>): ServiceResult<T>

    @kotlin.jvm.Throws(ServiceException::class)
    suspend fun <T : Any> POST(url: String, request: Any, responseType: Class<T>): ServiceResult<T>
    @kotlin.jvm.Throws(ServiceException::class)
    suspend fun <T : Any> PUT(url: String, responseType: Class<T>): ServiceResult<T>

    @kotlin.jvm.Throws(ServiceException::class)
    suspend fun <T : Any> PUT(url: String, request: Any, responseType: Class<T>): ServiceResult<T>

    @kotlin.jvm.Throws(ServiceException::class)
    suspend fun <T : Any> DELETE(url: String, responseType: Class<T>): ServiceResult<T>

    @kotlin.jvm.Throws(ServiceException::class)
    suspend fun <T : Any> DELETE(url: String, request: Any, responseType: Class<T>): ServiceResult<T>
}