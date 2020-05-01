package com.topjava.graduation.web.meal

import com.topjava.graduation.model.Meal
import org.slf4j.LoggerFactory
import com.topjava.graduation.service.MealService
import com.topjava.graduation.to.MealTo
import com.topjava.graduation.util.MealsUtil
import com.topjava.graduation.util.ValidationUtil
import com.topjava.graduation.web.SecurityUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.time.LocalDate
import java.time.LocalTime

@RestController
@RequestMapping(value = [MealRestController.REST_URL], produces = [MediaType.APPLICATION_JSON_VALUE])
class MealRestController : AbstractMealController() {

    companion object {
        const val REST_URL = "/rest/profile/meals"
    }

    @GetMapping("/{id}")
    override fun get(@PathVariable id: Int): Meal? {
        return super.get(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun delete(@PathVariable id: Int) {
        super.delete(id)
    }

    @GetMapping
    override fun getAll(): List<MealTo> {
        return super.getAll()
    }

    @PutMapping(value = ["/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    override fun update(@RequestBody meal: Meal, @PathVariable id: Int) {
        super.update(meal, id)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createWithLocation(@RequestBody meal: Meal): ResponseEntity<Meal> {
        val created = super.create(meal)

        val uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("$REST_URL/{id}")
                .buildAndExpand(created!!.id).toUri()

        return ResponseEntity.created(uriOfNewResource).body(created)
    }

    @GetMapping("/filter")
    override fun getBetween(
            @RequestParam startDate: LocalDate?,
            @RequestParam startTime: LocalTime?,
            @RequestParam endDate: LocalDate?,
            @RequestParam endTime: LocalTime?
    ): List<MealTo> {
       return super.getBetween(startDate, startTime, endDate, endTime)
    }
}