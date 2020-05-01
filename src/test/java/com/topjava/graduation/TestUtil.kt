package com.topjava.graduation

import com.topjava.graduation.web.json.JsonUtil
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions

object TestUtil {
    fun getContent(result: MvcResult): String {
        return result.response.contentAsString
    }

    fun <T> readFromJson(action: ResultActions, clazz: Class<T>): T {
        return JsonUtil.readValue(getContent(action.andReturn()), clazz)!!
    }

    fun <T> readFromJsonMvcResult(result: MvcResult, clazz: Class<T>): T {
        return JsonUtil.readValue(getContent(result), clazz)!!
    }

    fun <T> readListFromJsonMvcResult(result: MvcResult, clazz: Class<T>): MutableList<T> {
        return JsonUtil.readValues(getContent(result), clazz)!!
    }
}