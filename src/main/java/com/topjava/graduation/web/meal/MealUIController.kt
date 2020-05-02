package com.topjava.graduation.web.meal

import com.topjava.graduation.model.Meal
import com.topjava.graduation.model.isNew
import com.topjava.graduation.to.MealTo
import com.topjava.graduation.util.ValidationUtil
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ValidationUtils
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalTime
import javax.validation.Valid

@RestController
@RequestMapping("/ajax/profile/meals")
class MealUIController : AbstractMealController() {

    @GetMapping(value = ["/{id}"])
    override fun get(@PathVariable id: Int): Meal? {
        return super.get(id)
    }

    @DeleteMapping(value = ["/{id}"])
    override fun delete(@PathVariable id: Int) {
        super.delete(id)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun getAll(): List<MealTo> {
        return super.getAll()
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun createOrUpdate(@Valid meal: Meal, result: BindingResult): ResponseEntity<String> {
        if (result.hasErrors()) {
            return ValidationUtil.getErrorResponse(result)
        }
        if (meal.isNew()) {
            super.create(meal)
        } else {
            super.update(meal, meal.id!!)
        }
        return ResponseEntity.ok().build()
    }

    @GetMapping(value = ["/filter"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun getBetween(
            @RequestParam(value = "startDate", required = false) startDate: LocalDate?,
            @RequestParam(value = "startTime", required = false) startTime: LocalTime?,
            @RequestParam(value = "endDate", required = false) endDate: LocalDate?,
            @RequestParam(value = "endTime", required = false) endTime: LocalTime?): List<MealTo> {
        return super.getBetween(startDate, startTime, endDate, endTime)
    }
}