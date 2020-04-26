package com.topjava.graduation.service.datajpa

import com.topjava.graduation.MealTestData
import com.topjava.graduation.Profiles
import com.topjava.graduation.UserTestData
import com.topjava.graduation.service.AbstractMealServiceTest
import com.topjava.graduation.util.NotFoundException
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