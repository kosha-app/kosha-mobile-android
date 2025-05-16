package com.musica.phone

import android.content.Intent
import com.kosha.kosha_api.phone.HostUrlRepo
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MockDependenciesModule {

    @Provides
    fun provideHostUrlRepo(): HostUrlRepo = mock()

}