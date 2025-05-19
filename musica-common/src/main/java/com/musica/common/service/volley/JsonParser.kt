package com.musica.common.service.volley

import android.util.Log
import com.google.gson.GsonBuilder
import javax.inject.Inject

/**
 * Abstraction for JSON parsing operations.
 * This interface defines the method for mapping a JSON string to an object of a specified type.
 */
interface JsonParser {

    /**
     * Converts a JSON string to an object of the specified type.
     *
     * @param json The JSON string to parse.
     * @param clazz The class type to map the JSON to.
     * @return An object of type [T] if parsing is successful, or null if parsing fails or the input is invalid.
     */
    fun <T> mapJsonToObject(json: String?, clazz: Class<T>): T?

    fun mapObjectToJson(obj: Any?): String
}

/**
 * A [JsonParser] implementation that uses Gson for JSON parsing.
 *
 * This class maps a JSON string to an object of the specified type using the Gson library.
 * It safely handles null or invalid JSON inputs and returns null in case of failure.
 *
 * @constructor Injects an instance of [JsonParserImpl] via dependency injection.
 */
class JsonParserImpl @Inject constructor() : JsonParser {

    private val mapper = GsonBuilder().create()

    /**
     * Converts a JSON string to an object of the specified class type using Gson.
     * Returns null if the JSON is empty, invalid, or cannot be mapped to the specified class.
     *
     * @param json The JSON string to parse.
     * @param clazz The class type to map the JSON to.
     * @return An object of type [T] if parsing is successful, or null if parsing fails or input is invalid.
     */
    override fun <T> mapJsonToObject(json: String?, clazz: Class<T>): T? {
        return if (clazz == Void::class.java) {
                null
            } else {
                mapper.fromJson(json, clazz)
            }

    }

    override fun mapObjectToJson(obj: Any?): String {
        return try {
            mapper.toJson(obj)
        } catch (e: Exception){
            Log.e("JsonParserImpl", e.message.orEmpty())
            ""
        }
    }
}



