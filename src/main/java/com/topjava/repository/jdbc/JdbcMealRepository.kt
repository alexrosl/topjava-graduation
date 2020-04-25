package com.topjava.repository.jdbc

import com.topjava.model.Meal
import com.topjava.model.isNew
import com.topjava.repository.MealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.support.DataAccessUtils
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Repository
class JdbcMealRepository : MealRepository {

    private val ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal::class.java)

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate

    private lateinit var insertMeal: SimpleJdbcInsert

    @PostConstruct
    private fun init() {
        this.insertMeal = SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id")
    }


    override fun save(meal: Meal, userId: Int): Meal? {
        val map = MapSqlParameterSource()
                .addValue("id", meal.id)
                .addValue("user_id", userId)
                .addValue("date_time", meal.dateTime)
                .addValue("description", meal.description)
                .addValue("calories", meal.calories)

        if (meal.isNew()) {
            val newId = insertMeal.executeAndReturnKey(map)
            meal.id = newId.toInt()
        } else {

            //language=PostgreSQL
            if (namedParameterJdbcTemplate.update(
                            """UPDATE meals 
                                SET description=:description, calories=:calories, date_time=:date_time 
                                WHERE id=:id AND user_id=:user_id""".trimIndent()
                            , map) == 0) {
                return null
            }
        }
        return meal
    }

    override fun delete(id: Int, userId: Int): Boolean {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?",  id, userId) != 0
    }

    override fun get(id: Int, userId: Int): Meal? {
        val meals = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", ROW_MAPPER, id, userId)
        return DataAccessUtils.singleResult(meals)
    }

    override fun getAll(userId: Int): List<Meal> {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id = ? ORDER BY date_time DESC", ROW_MAPPER, userId)
    }

    override fun getBetweenInclusive(startDate: LocalDateTime?, endDate: LocalDateTime?, userId: Int): List<Meal> {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=? AND date_time >= ? AND date_time < ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, startDate, endDate
        )
    }
}