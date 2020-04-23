package com.topjava.service

import com.topjava.model.Meal
import com.topjava.repository.MealRepository
import com.topjava.util.ValidationUtil.checkNotFoundWithId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.time.LocalDate

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
        return repository.getBetweenInclusive(
                startDate,
                endDate,
                userId
        )
    }

    fun getAll(userId: Int): List<Meal> {
        return repository.getAll(userId)
    }

    fun update(meal: Meal, userId: Int) {
        Assert.notNull(meal, "meal must not be null")
        checkNotFoundWithId(repository.save(meal, userId), meal.id!!)
    }

    fun create(meal: Meal, userId: Int): Meal? {
        Assert.notNull(meal, "meal must not be null")
        return repository.save(meal, userId)
    }
}