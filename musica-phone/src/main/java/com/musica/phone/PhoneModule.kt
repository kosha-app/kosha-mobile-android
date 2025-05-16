package com.musica.phone

import com.kosha.kosha_api.phone.HostUrlRepo
import com.musica.dashboard.home.HomeScreen
import com.musica.phone.buildvariables.HostUrlRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
interface PhoneModule {

    @Binds
    fun bindHostUrlRepo(impl: HostUrlRepoImpl): HostUrlRepo

    companion object {

        @Provides
        @Named("base-url")
        fun provideBaseMCAUrl(): String {
           return BuildConfig.BASE_MCA_URL
        }
    }
}