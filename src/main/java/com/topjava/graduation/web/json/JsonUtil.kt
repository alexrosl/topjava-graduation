package com.topjava.graduation.web.json

import com.fasterxml.jackson.core.JsonProcessingException
import java.io.IOException

object JsonUtil {
    fun <T> readValues(json: String, clazz: Class<T>): MutableList<T>? {
        val reader = JacksonObjectMapper.mapper.readerFor(clazz)
        try {
            return reader.readValues<T>(json).readAll()
        } catch (e: IOException) {
            throw IllegalArgumentException("Invalid read array from JSON:\n '$json'", e)
        }
    }

    fun <T> readValue(json: String, clazz: Class<T>): T? {
        try {
            return JacksonObjectMapper.mapper.readValue(json, clazz)
        } catch (e: IOException) {
            throw IllegalArgumentException("Invalid read array from JSON:\n '$json'", e)
        }
    }

    fun <T> writeValue(obj: T): String? {
        try {
            return JacksonObjectMapper.mapper.writeValueAsString(obj)
        } catch (e: JsonProcessingException) {
            throw IllegalStateException("Invalid write to JSON:\n'$obj'", e)
        }
    }
}