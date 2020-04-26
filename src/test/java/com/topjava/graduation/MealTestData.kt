package com.topjava.graduation

import com.topjava.graduation.model.AbstractBaseEntity.Companion.START_SEQ
import com.topjava.graduation.model.Meal
import java.time.LocalDateTime
import java.time.Month

import org.assertj.core.api.Assertions.assertThat


object MealTestData {
    val MEAL1_ID: Int = START_SEQ + 2
    val ADMIN_MEAL_ID: Int = START_SEQ + 9

    val MEAL1: Meal = Meal(MEAL1_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)
    val MEAL2: Meal = Meal(MEAL1_ID + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000)
    val MEAL3: Meal = Meal(MEAL1_ID + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500)
    val MEAL4: Meal = Meal(MEAL1_ID + 3, LocalDateTime.of(2015, Month.MAY, 31, 0, 0), "Еда на граничное значение", 100)
    val MEAL5: Meal = Meal(MEAL1_ID + 4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500)
    val MEAL6: Meal = Meal(MEAL1_ID + 5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000)
    val MEAL7: Meal = Meal(MEAL1_ID + 6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    val ADMIN_MEAL1: Meal = Meal(ADMIN_MEAL_ID, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510)
    val ADMIN_MEAL2: Meal = Meal(ADMIN_MEAL_ID + 1, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500)

    val MEALS: List<Meal> = listOf(MEAL7, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1)

    fun assertMatch(actual: Meal, expected: Meal) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user")
    }

    fun assertMatch(actual: Iterable<Meal>, expected: Iterable<Meal?>?) {
        assertThat(actual).usingElementComparatorIgnoringFields("user").isEqualTo(expected)
    }
}