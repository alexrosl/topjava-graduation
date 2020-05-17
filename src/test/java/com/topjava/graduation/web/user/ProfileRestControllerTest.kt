package com.topjava.graduation.web.user

import com.topjava.graduation.TestUtil
import com.topjava.graduation.UserTestData
import com.topjava.graduation.UserTestData.ADMIN
import com.topjava.graduation.UserTestData.USER
import com.topjava.graduation.UserTestData.USER_ID
import com.topjava.graduation.model.User
import com.topjava.graduation.service.UserService
import com.topjava.graduation.to.UserTo
import com.topjava.graduation.util.ErrorType
import com.topjava.graduation.web.AbstractControllerTest
import com.topjava.graduation.web.json.JsonUtil
import com.topjava.graduation.web.user.ProfileRestController.Companion.REST_URL
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProfileRestControllerTest : AbstractControllerTest() {

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun testGet() {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect() {
                    result ->
                    UserTestData.assertMatch(TestUtil.readFromJsonMvcResult(result, User::class.java), UserTestData.USER)
                }
    }

    @Test
    fun testDelete() {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent)
        UserTestData.assertMatch(userService.getAll(), listOf(ADMIN))
    }

    @Test
    fun testUpdate() {
        val updated = User(UserTestData.USER)
        updated.name = "updatedName"
        updated.caloriesPerDay = 310
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)!!))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent)

        UserTestData.assertMatch(userService.get(USER_ID)!!, updated)
    }

    @Test
    fun testUpdateInvalid() {
        val updatedTo = UserTo(null, null, "password", null, 1500)!!

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)!!)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER.email, USER.password)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name))
    }
}