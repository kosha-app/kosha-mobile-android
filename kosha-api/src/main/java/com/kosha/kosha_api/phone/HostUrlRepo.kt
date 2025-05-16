package com.kosha.kosha_api.phone

interface HostUrlRepo {
    fun getBaseHostUrl(): String

    fun getLocalBaseHoseURL(): String
}