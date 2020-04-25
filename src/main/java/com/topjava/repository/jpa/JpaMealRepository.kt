package com.topjava.repository.jpa

import com.topjava.model.Meal
import com.topjava.model.User
import com.topjava.model.isNew
import com.topjava.repository.MealRepository
import com.topjava.util.DateTimeUtil
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional(readOnly = true)
open class JpaMealRepository : MealRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    @Transactional
    override fun save(meal: Meal, userId: Int): Meal? {
        if (!meal.isNew() && get(meal.id!!, userId) == null) {
            return null
        }
        meal.user = em.getReference(User::class.java, userId)
        return if (meal.isNew()) {
            em.persist(meal)
            meal
        } else {
            em.merge(meal)
        }
    }

    @Transactional
    override fun delete(id: Int, userId: Int): Boolean {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0
    }

    override fun get(id: Int, userId: Int): Meal? {
        val meal = em.find(Meal::class.java, id)
        return if (meal != null && meal.user?.id == userId) {
            meal
        } else {
            null
        }
    }

    override fun getAll(userId: Int): List<Meal> {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal::class.java)
                .setParameter("userId", userId)
                .resultList
    }

    override fun getBetweenInclusive(startDate: LocalDateTime?, endDate: LocalDateTime?, userId: Int): List<Meal> {
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal::class.java)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .resultList
    }

}