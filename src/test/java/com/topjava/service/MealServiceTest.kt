package com.topjava.service

import com.topjava.MealTestData
import com.topjava.MealTestData.ADMIN_MEAL1
import com.topjava.MealTestData.ADMIN_MEAL_ID
import com.topjava.MealTestData.MEAL1
import com.topjava.MealTestData.MEAL1_ID
import com.topjava.MealTestData.MEAL2
import com.topjava.MealTestData.MEAL3
import com.topjava.MealTestData.MEAL4
import com.topjava.MealTestData.MEALS
import com.topjava.MealTestData.assertMatch
import com.topjava.UserTestData.ADMIN_ID
import com.topjava.UserTestData.USER_ID
import com.topjava.model.Meal
import com.topjava.util.NotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import kotlin.test.assertEquals

@SpringJUnitConfig(locations = [
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-db.xml"]
) //@ExtendWith(SpringExtension.class)
@Sql(scripts = ["classpath:db/populateDB.sql"], config = SqlConfig(encoding = "UTF-8"))
class MealServiceTest {

    @Autowired
    private lateinit var service: MealService

    @Test
    fun testGet() {
        val actual = service.get(ADMIN_MEAL_ID, ADMIN_ID)!!
        assertMatch(actual, ADMIN_MEAL1)
    }

    @Test
    fun testGetNotOwn() {
        Assertions.assertThrows(NotFoundException::class.java) { service.get(MEAL1_ID, ADMIN_ID) }
    }

    @Test
    fun testGetNotFound() {
        Assertions.assertThrows(NotFoundException::class.java) { service.get(1, ADMIN_ID) }
    }

    @Test
    fun testDelete() {
        service.delete(MEAL1_ID, USER_ID)
        Assertions.assertThrows(NotFoundException::class.java) { service.get(MEAL1_ID, USER_ID) }
    }

    @Test
    fun testDeleteNotFound() {
        Assertions.assertThrows(NotFoundException::class.java) { service.get(1, USER_ID) }
    }

    @Test
    fun testDeleteNotOwn() {
        Assertions.assertThrows(NotFoundException::class.java) { service.get(MEAL1_ID, ADMIN_ID) }
    }

    @Test
    fun testGetBetweenDates() {
        val betweenDates = service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID)
        assertMatch(betweenDates, listOf(MEAL4, MEAL3, MEAL2, MEAL1))
    }

    @Test
    fun testGetAll() {
        val all = service.getAll(USER_ID)
        assertMatch(all, MEALS)
    }

    @Test
    fun testUpdate() {
        val updated = Meal(MEAL1_ID, MEAL1.dateTime, "Обновленный завтрак", 200)
        service.update(updated, USER_ID)
        assertMatch(service.get(MEAL1_ID, USER_ID)!!, updated)
    }

    @Test
    fun testUpdateNotFound() {
        val exception = Assertions.assertThrows(NotFoundException::class.java) { service.update(MEAL1, ADMIN_ID) }
        assertEquals("Not found entity with id=$MEAL1_ID", exception.message)
    }

    @Test
    fun testCreate() {
        val newMeal = Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 18, 0), "Созданный ужин", 300)
        val created = service.create(newMeal, USER_ID)
        newMeal.id = created!!.id
        assertMatch(created, newMeal)
        assertMatch(service.get(created.id!!, USER_ID)!!, newMeal)
    }


}