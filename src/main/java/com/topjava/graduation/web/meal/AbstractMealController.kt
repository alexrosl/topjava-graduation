package com.topjava.graduation.web.meal

import com.topjava.graduation.model.Meal
import com.topjava.graduation.service.MealService
import com.topjava.graduation.to.MealTo
import com.topjava.graduation.util.MealsUtil
import com.topjava.graduation.util.ValidationUtil
import com.topjava.graduation.web.SecurityUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.time.LocalTime

abstract class AbstractMealController {
    companion object {
        internal val logger = LoggerFactory.getLogger(MealRestController::class.java)
    }

    @Autowired
    private lateinit var service: MealService

    open fun get(id: Int): Meal? {
        val userId = SecurityUtil.authUserId()
        logger.info("get meal $id for user $userId")
        return service.get(id, userId)
    }

    open fun delete(id: Int) {
        val userId = SecurityUtil.authUserId()
        logger.info("delete meal $id for user $userId")
        service.delete(id, userId)
    }

    open fun getAll(): List<MealTo> {
        val userId = SecurityUtil.authUserId()
        logger.info("getAll for user $userId")
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay())
    }

    fun create(meal: Meal): Meal? {
        val userId = SecurityUtil.authUserId()
        ValidationUtil.checkNew(meal)
        logger.info("create $meal for user $userId")
        return service.create(meal, userId)
    }

    open fun update(meal: Meal, id: Int) {
        val userId = SecurityUtil.authUserId()
        ValidationUtil.assureIdConsistent(meal, id)
        logger.info("update $meal for user $userId")
        service.update(meal, userId)
    }

    open fun getBetween(startDate: LocalDate?, startTime: LocalTime?,
                        endDate: LocalDate?, endTime: LocalTime?) : List<MealTo> {
        val userId = SecurityUtil.authUserId()
        logger.info("getBetween dates ($startDate - $endDate) time ($startTime - $endTime) for user $userId")

        val mealsDateFiltered = service.getBetweenDates(startDate, endDate, userId)
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime)
    }
}