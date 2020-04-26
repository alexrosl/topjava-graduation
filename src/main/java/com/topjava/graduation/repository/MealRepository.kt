package com.topjava.graduation.repository

import com.topjava.graduation.model.Meal
import com.topjava.graduation.util.DateTimeUtil
import java.time.LocalDate
import java.time.LocalDateTime

interface MealRepository {
    fun save(meal: Meal, userId: Int): Meal?
    fun delete(id: Int, userId: Int): Boolean
    fun get(id: Int, userId: Int): Meal?
    fun getAll(userId: Int): List<Meal>
    fun getBetweenInclusive(startDate: LocalDate?, endDate: LocalDate?, userId: Int): List<Meal> {
        return getBetweenInclusive(DateTimeUtil.getStartInclusive(startDate), DateTimeUtil.getEndInclusive(endDate), userId)
    }
    fun getBetweenInclusive(startDate: LocalDateTime?, endDate: LocalDateTime?, userId: Int): List<Meal>
    fun getWithUser(id: Int, userId: Int): Meal? {
        throw UnsupportedOperationException()
    }
}