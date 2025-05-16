package com.musica.phone.buildvariables

import com.kosha.kosha_api.phone.HostUrlRepo
import javax.inject.Inject
import javax.inject.Named

class HostUrlRepoImpl @Inject constructor(
    @Named("base-url") private val baseUrl : String
): HostUrlRepo {

    override fun getBaseHostUrl(): String {
        return baseUrl
    }

    override fun getLocalBaseHoseURL(): String {
        return "http://10.0.2.2:8080"
    }
}