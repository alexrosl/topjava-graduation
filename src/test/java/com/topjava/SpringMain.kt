package com.topjava

import com.topjava.model.Role
import com.topjava.model.User
import com.topjava.to.MealTo
import com.topjava.web.meal.MealRestController
import com.topjava.web.user.AdminRestController
import org.springframework.context.support.GenericXmlApplicationContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.util.*

fun main() {
    GenericXmlApplicationContext().use { appCtx ->
        appCtx.environment.setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION)
        appCtx.load("spring/spring-app.xml", "spring/spring-db.xml")
        appCtx.refresh()
        println("Bean definition names: " + Arrays.toString(appCtx.beanDefinitionNames))
        val adminUserController = appCtx.getBean(AdminRestController::class.java)
        adminUserController.create(User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN))
        println()
        val mealController = appCtx.getBean(MealRestController::class.java)
        val filteredMealsWithExcess: List<MealTo> = mealController.getBetween(
                LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0))
        filteredMealsWithExcess.forEach(System.out::println)
    }
}