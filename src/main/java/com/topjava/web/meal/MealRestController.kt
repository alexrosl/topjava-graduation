package com.topjava.web.meal

import com.topjava.model.AbstractBaseEntity
import com.topjava.model.Meal
import org.slf4j.LoggerFactory
import com.topjava.service.MealService
import com.topjava.to.MealTo
import com.topjava.util.MealsUtil
import com.topjava.util.ValidationUtil
import com.topjava.web.SecurityUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import java.time.LocalDate
import java.time.LocalTime

@Controller
class MealRestController {

    companion object {
        internal val logger = LoggerFactory.getLogger(MealRestController::class.java)
    }

    @Autowired
    private lateinit var service: MealService

    fun get(id: Int): Meal? {
        val userId = SecurityUtil.authUserId()
        logger.info("get meal $id for user $userId")
        return service.get(id, userId)
    }

    fun delete(id: Int) {
        val userId = SecurityUtil.authUserId()
        logger.info("delete meal $id for user $userId")
        service.delete(id, userId)
    }

    fun getAll(): List<MealTo> {
        val userId = SecurityUtil.authUserId()
        logger.info("getAll for user $userId")
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay())
    }

    fun create(meal: Meal): Meal {
        val userId = SecurityUtil.authUserId()
        ValidationUtil.checkNew(meal)
        logger.info("create $meal for user $userId")
        return service.create(meal, userId)
    }

    fun update(meal: Meal, id: Int) {
        val userId = SecurityUtil.authUserId()
        ValidationUtil.assureIdConsistent(meal, id)
        logger.info("update $meal for user $userId")
        service.update(meal, userId)
    }

    fun getBetween(startDate: LocalDate?, startTime: LocalTime?,
                    endDate: LocalDate?, endTime: LocalTime?) : List<MealTo> {
        val userId = SecurityUtil.authUserId()
        logger.info("getBetween dates ($startDate - $endDate) time ($startTime - $endTime) for user $userId")

        val mealsDateFiltered = service.getBetweenDates(startDate, endDate, userId)
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime)
    }
}