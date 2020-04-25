package com.topjava.service

import com.topjava.UserTestData
import com.topjava.model.Role
import com.topjava.model.User
import com.topjava.util.NotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import java.util.*

abstract class AbstractUserServiceTest : AbstractServiceTest() {

    @Autowired
    lateinit var service: UserService

    @Test
    fun create() {
        val newUser = User(null, "New", "new@gmail.com", "password", 1555, false, Date(), listOf(Role.ROLE_USER))
        val created = service.create(newUser)
        newUser.id = created!!.id
        UserTestData.assertMatch(service.get(created.id!!)!!, newUser)
    }

    @Test
    fun duplicateEmailCreate() {
        Assertions.assertThrows(DataAccessException::class.java)
        { service.create(User(null, "Duplicate", "email@mail.ru", "passsswrod", Role.ROLE_USER)) }
    }

    @Test
    fun delete() {
        service.delete(UserTestData.USER_ID)
        UserTestData.assertMatch(service.getAll(), listOf(UserTestData.ADMIN))
    }

    @Test
    fun deleteNotFound() {
        Assertions.assertThrows(NotFoundException::class.java) { service.delete(1) }
    }

    @Test
    fun get() {
        UserTestData.assertMatch(service.get(UserTestData.USER_ID)!!, UserTestData.USER)
    }

    @Test
    fun getNotFound() {
        Assertions.assertThrows(NotFoundException::class.java) { service.get(1) }
    }

    @Test
    fun getByEmail() {
        UserTestData.assertMatch(service.getByEmail(UserTestData.USER.email!!)!!, UserTestData.USER)
    }

    @Test
    fun getAll() {
        val all = service.getAll()
        UserTestData.assertMatch(all, listOf(UserTestData.ADMIN, UserTestData.USER))
    }

    @Test
    fun update() {
        val updated = User(UserTestData.USER)
        updated.name = "updatedName"
        updated.caloriesPerDay = 310
        service.update(updated)
        UserTestData.assertMatch(service.get(UserTestData.USER_ID)!!, updated)
    }
}