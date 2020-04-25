package com.topjava.service.datajpa

import com.topjava.MealTestData
import com.topjava.Profiles
import com.topjava.UserTestData
import com.topjava.service.AbstractMealServiceTest
import com.topjava.util.NotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles(Profiles.DATAJPA)
class DataJpaMealServiceTest : AbstractMealServiceTest() {

    @Test
    fun testGetWithUser() {
        val adminMeal = service.getWithUser(MealTestData.ADMIN_MEAL_ID, UserTestData.ADMIN_ID)!!
        MealTestData.assertMatch(adminMeal, MealTestData.ADMIN_MEAL1)
        UserTestData.assertMatch(adminMeal.user!!, UserTestData.ADMIN)
    }

    @Test
    fun testGetWithUserNotFound() {
        Assertions.assertThrows(NotFoundException::class.java) { service.getWithUser(1, UserTestData.ADMIN_ID) }
    }
}