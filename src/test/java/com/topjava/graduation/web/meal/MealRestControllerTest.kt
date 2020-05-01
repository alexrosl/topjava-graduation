package com.topjava.graduation.web.meal

import com.topjava.graduation.MealTestData
import com.topjava.graduation.MealTestData.MEAL1
import com.topjava.graduation.MealTestData.MEAL1_ID
import com.topjava.graduation.MealTestData.MEAL5
import com.topjava.graduation.MealTestData.MEALS
import com.topjava.graduation.MealTestData.assertMatch
import com.topjava.graduation.TestUtil
import com.topjava.graduation.TestUtil.readFromJsonMvcResult
import com.topjava.graduation.UserTestData.USER
import com.topjava.graduation.UserTestData.USER_ID
import com.topjava.graduation.model.AbstractBaseEntity.Companion.START_SEQ
import com.topjava.graduation.model.Meal
import com.topjava.graduation.service.MealService
import com.topjava.graduation.to.MealTo
import com.topjava.graduation.util.MealsUtil
import com.topjava.graduation.util.NotFoundException
import com.topjava.graduation.web.AbstractControllerTest
import com.topjava.graduation.web.json.JsonUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.time.Month

import org.assertj.core.api.Assertions.assertThat


class MealRestControllerTest : AbstractControllerTest() {
    private val REST_URL = MealRestController.REST_URL + "/"

    @Autowired
    private lateinit var mealService: MealService

    @Test
    fun testGet() {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
                .andDo(MockMvcResultHandlers.print())
                .andExpect {
                    status().isOk
                    content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                }
                .andExpect() { result ->  assertMatch(readFromJsonMvcResult(result, Meal::class.java), MEAL1) }
    }

    @Test
    fun testDelete() {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID))
                .andExpect(status().isNoContent)
        Assertions.assertThrows(NotFoundException::class.java) { mealService.get(MEAL1_ID, USER_ID) }
    }

    @Test
    fun testUpdate() {
        val updated = Meal(MealTestData.MEAL1_ID, MealTestData.MEAL1.dateTime, "Обновленный завтрак", 200)

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)!!))
                .andExpect(status().isNoContent)

        assertMatch(mealService.get(MEAL1_ID, START_SEQ)!!, updated)
    }

    @Test
    fun createWithLocation() {
        val newMeal = Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 18, 0), "Созданный ужин", 300)
        val action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)!!))

        val created = TestUtil.readFromJson(action, Meal::class.java)
        val newId = created.id!!
        newMeal.id = newId
        assertMatch(created, newMeal)
        assertMatch(mealService.get(newId, USER_ID)!!, newMeal)
    }

    @Test
    fun getAll() {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect() {
                    result ->
                    assertThat(TestUtil.readListFromJsonMvcResult(result, MealTo::class.java))
                            .isEqualTo(MealsUtil.getTos(MEALS, USER.caloriesPerDay))}
    }

    @Test
    fun testFilter() {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", "2015-05-30").param("startTime", "07:00")
                .param("endDate", "2015-05-31").param("endTime", "11:00"))
                .andExpect(status().isOk)
                .andDo(MockMvcResultHandlers.print())
                .andExpect() {
                    result ->
                    assertThat(TestUtil.readListFromJsonMvcResult(result, MealTo::class.java))
                            .isEqualTo(listOf(MealsUtil.createTo(MEAL5, true), MealsUtil.createTo(MEAL1, false)))
                }
    }

    @Test
    fun testFilterAll() {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter?=startDate=&endTime="))
                .andExpect(status().isOk)
                .andExpect() {
                    result ->
                    assertThat(TestUtil.readListFromJsonMvcResult(result, MealTo::class.java))
                            .isEqualTo(MealsUtil.getTos(MEALS, USER.caloriesPerDay))
                }
    }
}