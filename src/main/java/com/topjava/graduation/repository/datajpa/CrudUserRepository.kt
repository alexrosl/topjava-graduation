package com.topjava.graduation.repository.datajpa

import com.topjava.graduation.model.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface CrudUserRepository : JpaRepository<User, Int> {
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    fun delete(@Param("id") id: Int): Int
    fun getByEmail(email: String?): User?

    @EntityGraph(attributePaths = ["meals"], type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u WHERE u.id=?1")
    fun getWithMeals(id: Int): User?
}