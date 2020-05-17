package com.topjava.graduation.web.user

import com.topjava.graduation.TestUtil
import com.topjava.graduation.TestUtil.readFromJson
import com.topjava.graduation.TestUtil.readFromJsonMvcResult
import com.topjava.graduation.UserTestData.ADMIN
import com.topjava.graduation.UserTestData.ADMIN_ID
import com.topjava.graduation.UserTestData.USER
import com.topjava.graduation.UserTestData.USER_ID
import com.topjava.graduation.UserTestData.assertMatch
import com.topjava.graduation.model.Role
import com.topjava.graduation.model.User
import com.topjava.graduation.service.UserService
import com.topjava.graduation.util.ErrorType
import com.topjava.graduation.util.NotFoundException
import com.topjava.graduation.web.AbstractControllerTest
import com.topjava.graduation.web.json.JsonUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

class AdminRestControllerTest : AbstractControllerTest() {
    private val REST_URL = AdminRestController.REST_URL + "/"

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun testGet() {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect() {
                    result ->
                    assertMatch(readFromJsonMvcResult(result, User::class.java), ADMIN)
                }
    }

    @Test
    fun testGetByEmail() {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "by?email=" + USER.email))
                .andExpect(status().isOk)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect() {
                    result ->
                    assertMatch(readFromJsonMvcResult(result, User::class.java), USER)
                }
    }

    @Test
    fun testDelete() {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + USER_ID))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent)
        Assertions.assertThrows(NotFoundException::class.java) { userService.get(USER_ID) }
    }

    @Test
    fun testUpdate() {
        val updated = User(USER)
        updated.name = "updatedName"
        updated.caloriesPerDay = 310
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)!!))
                .andExpect(status().isNoContent)

        assertMatch(userService.get(USER_ID)!!, updated)
    }

    @Test
    fun testUpdateInvalid() {
        val updated = User(USER)
        updated.name = ""
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)!!)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(ADMIN.email, ADMIN.password)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name))

    }

    @Test
    fun testCreateWithLocation() {
        val newUser = User(null, "New", "new@gmail.com", "password", 1555, false, Date(), listOf(Role.ROLE_USER))
        val action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)!!))
                .andExpect(status().isCreated)

        val created = readFromJson(action, User::class.java)
        val newId = created.id!!
        newUser.id = newId
        assertMatch(created, newUser)
        assertMatch(userService.get(newId)!!, newUser)
    }

    @Test
    fun testGetAll() {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect() {
                    result ->
                    assertMatch(TestUtil.readListFromJsonMvcResult(result, User::class.java),
                            listOf(ADMIN, USER))
                }
    }
}