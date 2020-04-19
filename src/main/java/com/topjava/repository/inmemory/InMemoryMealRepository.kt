package com.topjava.repository.inmemory

import com.topjava.model.Meal
import com.topjava.repository.MealRepository
import com.topjava.util.MealsUtil
import com.topjava.util.Util
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.Month
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryMealRepository : MealRepository {
    private var usersMealsMap = ConcurrentHashMap<Int, InMemoryBaseRepository<Meal>>()

    init {
        MealsUtil.MEALS.forEach { save(it, InMemoryUserRepository.USER_ID) }

        save(Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510), InMemoryUserRepository.ADMIN_ID);
        save(Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500), InMemoryUserRepository.ADMIN_ID);
    }

    override fun save(meal: Meal, userId: Int): Meal {
        val meals = usersMealsMap.computeIfAbsent(userId) { InMemoryBaseRepository() }
        return meals.save(meal)
    }

    override fun delete(id: Int, userId: Int): Boolean {
        val meals = usersMealsMap[userId]
        return meals != null && meals.delete(id)
    }

    override fun get(id: Int, userId: Int): Meal? {
        return usersMealsMap[userId]?.get(id)
    }

    override fun getAll(userId: Int): List<Meal> {
        return getAllFiltered(userId) { true }
    }

    override fun getBetween(startDate: LocalDateTime?, endDate: LocalDateTime?, userId: Int): List<Meal> {
        return getAllFiltered(userId) { Util.isBetweenInclusive(it.dateTime, startDate, endDate) }
    }

    private fun getAllFiltered(userId: Int, predicate: (Meal) -> Boolean): List<Meal> {
        val meals = usersMealsMap[userId]
        return meals?.getCollection()
                ?.filter(predicate)
                ?.sortedBy { it.dateTime }
                ?.reversed()
                ?: emptyList()
    }

}