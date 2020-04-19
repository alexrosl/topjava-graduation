package com.topjava.repository

import com.topjava.model.Meal
import java.time.LocalDateTime

interface MealRepository {
    fun save(meal: Meal, userId: Int): Meal
    fun delete(id: Int, userId: Int): Boolean
    fun get(id: Int, userId: Int): Meal?
    fun getAll(userId: Int): List<Meal>
    fun getBetween(startDate: LocalDateTime?, endDate: LocalDateTime?, userId: Int): List<Meal>
}