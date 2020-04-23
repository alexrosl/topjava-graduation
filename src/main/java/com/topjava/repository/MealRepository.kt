package com.topjava.repository

import com.topjava.model.Meal
import java.time.LocalDate

interface MealRepository {
    fun save(meal: Meal, userId: Int): Meal?
    fun delete(id: Int, userId: Int): Boolean
    fun get(id: Int, userId: Int): Meal?
    fun getAll(userId: Int): List<Meal>
    fun getBetweenInclusive(startDate: LocalDate?, endDate: LocalDate?, userId: Int): List<Meal>
}