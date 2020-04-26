package com.topjava.graduation.service.datajpa

import com.topjava.graduation.MealTestData
import com.topjava.graduation.Profiles
import com.topjava.graduation.UserTestData
import com.topjava.graduation.service.AbstractUserServiceTest
import com.topjava.graduation.util.NotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles(Profiles.DATAJPA)
class DataJpaUserServiceTest : AbstractUserServiceTest() {

    @Test
    fun testGetWithMeals() {
        val user = service.getWithMeals(UserTestData.USER_ID)!!
        UserTestData.assertMatch(user, UserTestData.USER)
        MealTestData.assertMatch(user.meals!!, MealTestData.MEALS)
    }

    @Test
    fun testGetWithMealsNotFound() {
        Assertions.assertThrows(NotFoundException::class.java) { service.getWithMeals(1) }
    }
}