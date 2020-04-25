package com.topjava.service

import com.topjava.MealTestData
import com.topjava.UserTestData
import com.topjava.model.Meal
import com.topjava.util.NotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import kotlin.test.assertEquals

abstract class AbstractMealServiceTest : AbstractServiceTest() {
    @Autowired
    lateinit var service: MealService

    @Test
    fun testGet() {
        val actual = service.get(MealTestData.ADMIN_MEAL_ID, UserTestData.ADMIN_ID)!!
        MealTestData.assertMatch(actual, MealTestData.ADMIN_MEAL1)
    }

    @Test
    fun testGetNotOwn() {
        Assertions.assertThrows(NotFoundException::class.java) { service.get(MealTestData.MEAL1_ID, UserTestData.ADMIN_ID) }
    }

    @Test
    fun testGetNotFound() {
        Assertions.assertThrows(NotFoundException::class.java) { service.get(1, UserTestData.ADMIN_ID) }
    }

    @Test
    fun testDelete() {
        service.delete(MealTestData.MEAL1_ID, UserTestData.USER_ID)
        Assertions.assertThrows(NotFoundException::class.java) { service.get(MealTestData.MEAL1_ID, UserTestData.USER_ID) }
    }

    @Test
    fun testDeleteNotFound() {
        Assertions.assertThrows(NotFoundException::class.java) { service.get(1, UserTestData.USER_ID) }
    }

    @Test
    fun testDeleteNotOwn() {
        Assertions.assertThrows(NotFoundException::class.java) { service.get(MealTestData.MEAL1_ID, UserTestData.ADMIN_ID) }
    }

    @Test
    fun testGetBetweenDates() {
        val betweenDates = service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), UserTestData.USER_ID)
        MealTestData.assertMatch(betweenDates, listOf(MealTestData.MEAL3, MealTestData.MEAL2, MealTestData.MEAL1))
    }

    @Test
    fun testGetBetweenWithNullDates() {
        MealTestData.assertMatch(service.getBetweenDates(null, null, UserTestData.USER_ID), MealTestData.MEALS)
    }

    @Test
    fun testGetAll() {
        val all = service.getAll(UserTestData.USER_ID)
        MealTestData.assertMatch(all, MealTestData.MEALS)
    }

    @Test
    fun testUpdate() {
        val updated = Meal(MealTestData.MEAL1_ID, MealTestData.MEAL1.dateTime, "Обновленный завтрак", 200)
        service.update(updated, UserTestData.USER_ID)
        MealTestData.assertMatch(service.get(MealTestData.MEAL1_ID, UserTestData.USER_ID)!!, updated)
    }

    @Test
    fun testUpdateNotFound() {
        val exception = Assertions.assertThrows(NotFoundException::class.java) { service.update(MealTestData.MEAL1, UserTestData.ADMIN_ID) }
        assertEquals("Not found entity with id=${MealTestData.MEAL1_ID}", exception.message)
    }

    @Test
    fun testCreate() {
        val newMeal = Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 18, 0), "Созданный ужин", 300)
        val created = service.create(newMeal, UserTestData.USER_ID)
        newMeal.id = created!!.id
        MealTestData.assertMatch(created, newMeal)
        MealTestData.assertMatch(service.get(created.id!!, UserTestData.USER_ID)!!, newMeal)
    }
}