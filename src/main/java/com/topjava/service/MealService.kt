package com.topjava.service

import com.topjava.model.Meal
import com.topjava.repository.MealRepository
import com.topjava.util.DateTimeUtil
import com.topjava.util.ValidationUtil.checkNotFoundWithId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime

@Service
class MealService() {

    @Autowired
    private lateinit var repository: MealRepository

    fun get(id: Int, userId: Int) : Meal? {
        return checkNotFoundWithId(repository.get(id, userId), id)
    }

    fun delete(id: Int, userId: Int) {
        checkNotFoundWithId(repository.delete(id, userId), id)
    }

    fun getBetweenDates(startDate: LocalDate?, endDate: LocalDate?, userId: Int): List<Meal> {
        return repository.getBetween(
                DateTimeUtil.createDateTime(startDate, LocalDate.MIN, LocalTime.MIN),
                DateTimeUtil.createDateTime(endDate, LocalDate.MAX, LocalTime.MAX),
                userId
        )
    }

    fun getAll(userId: Int): List<Meal> {
        return repository.getAll(userId)
    }

    fun update(meal: Meal, userId: Int) {
        checkNotFoundWithId(repository.save(meal, userId), meal.id!!)
    }

    fun create(meal: Meal, userId: Int): Meal? {
        return repository.save(meal, userId)
    }
}