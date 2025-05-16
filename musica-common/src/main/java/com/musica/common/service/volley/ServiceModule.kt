package com.musica.common.service.volley

import android.content.Context
import com.android.volley.toolbox.Volley
import com.musica.common.service.interceptor.LoggingInterceptor
import dagger.Binds
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
interface ServiceModule {

    @Binds
    fun bindJsonParser(impl: JsonParserImpl): JsonParser

    @Binds
    fun bindErrorHandler(impl: ErrorHandlerImpl): ErrorHandler

    companion object {

        @Provides
        @Singleton
        fun providesService(
            @ApplicationContext context: Context,
            jsonParser: JsonParser,
            errorHandler: ErrorHandler,
        ): IService {
            val interceptor = LoggingInterceptor()
            val client: okhttp3.OkHttpClient = okhttp3.OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val requestQue = Volley.newRequestQueue(context, OkHttpStack(client))

            return ServiceImpl(requestQue, jsonParser, errorHandler)
        }
    }
}
