package com.topjava.util

import com.topjava.model.Meal
import com.topjava.to.MealTo
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.util.function.Predicate

object MealsUtil {
    val DEFAULT_CALORIES_PER_DAY = 2000
    val MEALS = listOf<Meal>(
            Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            Meal(null, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            Meal(null, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            Meal(null, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    )

    fun getTos(meals: Collection<Meal>, caloriesPerDay: Int): List<MealTo> {
        return getFiltered(meals, caloriesPerDay) { true }
    }

    fun getFilteredTos(meals: Collection<Meal>, caloriesPerDay: Int, startTime: LocalTime?, endTime: LocalTime?): List<MealTo> {
        return getFiltered(meals, caloriesPerDay) { meal-> Util.isBetweenInclusive(meal.dateTime.toLocalTime(), startTime, endTime) }
    }

    private fun getFiltered(meals: Collection<Meal>, caloriesPerDay: Int, predicate: (Meal) -> Boolean): List<MealTo> {
        val caloriesSumByDate =
                meals.groupBy { it.dateTime }
                        .mapValues { entry ->
                            entry.value.map { it.calories }.sum()
                        }
        return meals
                .filter ( predicate )
                .map { createTo(it, caloriesSumByDate.getValue(it.dateTime) > caloriesPerDay) }
    }

    private fun createTo(meal: Meal, excess: Boolean): MealTo {
        return MealTo(meal.id, meal.dateTime, meal.description, meal.calories, excess)
    }

}