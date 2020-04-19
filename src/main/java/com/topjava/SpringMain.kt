package com.topjava

import com.topjava.model.Role
import com.topjava.model.User
import com.topjava.repository.jdbc.JdbcMealRepository
import com.topjava.web.meal.MealRestController
import com.topjava.web.user.AdminRestController
import org.springframework.context.support.ClassPathXmlApplicationContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month

fun main() {
    val appCtx = ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")
    appCtx.use {
        println(appCtx.beanDefinitionNames.contentToString())

        val jdbcMealRepository = appCtx.getBean(JdbcMealRepository::class.java)
        val allMealsForUser = jdbcMealRepository.getAll(100000)
        println(allMealsForUser)

        val adminRestController = appCtx.getBean(AdminRestController::class.java)
        adminRestController.create(
                User(null,
                "userName",
                "email@mail.ru",
                "password",
                        Role.ROLE_ADMIN,
                        Role.ROLE_ADMIN))
        println()
        val allUsers = adminRestController.getAll()
        println(allUsers)

        val mealController = appCtx.getBean(MealRestController::class.java)
        val allMeals = mealController.getAll()
        println(allMeals.toString())

        val filteredMealWithExcess =
                mealController.getBetween(
                        LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                        LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0)
                )
        filteredMealWithExcess.forEach { println(it) }
    }
}