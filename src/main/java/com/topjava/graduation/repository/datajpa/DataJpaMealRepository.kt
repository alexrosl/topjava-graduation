package com.topjava.graduation.repository.datajpa

import com.topjava.graduation.model.Meal
import com.topjava.graduation.model.isNew
import com.topjava.graduation.repository.MealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class DataJpaMealRepository : MealRepository {

    @Autowired
    private lateinit var crudMealRepository: CrudMealRepository

    @Autowired
    private lateinit var crudUserRepository: CrudUserRepository

    override fun save(meal: Meal, userId: Int): Meal? {
        if (!meal.isNew() && get(meal.id!!, userId) == null) {
            return null
        }
        meal.user = crudUserRepository.getOne(userId)
        return crudMealRepository.save(meal)
    }

    override fun delete(id: Int, userId: Int): Boolean {
        return crudMealRepository.delete(id, userId) != 0
    }

    override fun get(id: Int, userId: Int): Meal? {
        return crudMealRepository.findById(id).filter { it.user?.id == userId }.orElse(null)
    }

    override fun getAll(userId: Int): List<Meal> {
        return crudMealRepository.getAll(userId)
    }

    override fun getBetweenInclusive(startDate: LocalDateTime?, endDate: LocalDateTime?, userId: Int): List<Meal> {
        return crudMealRepository.getBetween(startDate, endDate, userId)
    }

    override fun getWithUser(id: Int, userId: Int): Meal? {
        return crudMealRepository.getWithUser(id, userId)
    }
}