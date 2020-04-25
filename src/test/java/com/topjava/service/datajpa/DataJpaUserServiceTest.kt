package com.topjava.service.datajpa

import com.topjava.MealTestData
import com.topjava.Profiles
import com.topjava.UserTestData
import com.topjava.service.AbstractUserServiceTest
import com.topjava.util.NotFoundException
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