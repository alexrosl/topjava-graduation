package com.topjava.service

import com.topjava.UserTestData
import com.topjava.UserTestData.ADMIN
import com.topjava.UserTestData.USER
import com.topjava.UserTestData.USER_ID
import com.topjava.UserTestData.assertMatch
import com.topjava.model.Role
import com.topjava.model.User
import com.topjava.util.NotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.dao.DuplicateKeyException
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.util.*

@SpringJUnitConfig(locations = [
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-db.xml"]
) //@ExtendWith(SpringExtension.class)
@Sql(scripts = ["classpath:db/populateDB.sql"], config = SqlConfig(encoding = "UTF-8"))
class UserServiceTest {

    @Autowired
    private lateinit var service: UserService

    @Test
    fun create() {
        val newUser = User(null, "New", "new@gmail.com", "password", 1555, false, Date(), listOf(Role.ROLE_USER))
        val created = service.create(newUser)
        newUser.id = created!!.id
        UserTestData.assertMatch(service.get(created.id!!)!!, newUser)
    }

    @Test
    fun duplicateEmailCreate() {
        assertThrows(DataAccessException::class.java)
        { service.create(User(null, "Duplicate", "email@mail.ru", "passsswrod", Role.ROLE_USER)) }
    }

    @Test
    fun delete() {
        service.delete(USER_ID)
        assertMatch(service.getAll(), listOf(ADMIN))
    }

    @Test
    fun deleteNotFound() {
        assertThrows(NotFoundException::class.java) { service.delete(1) }
    }

    @Test
    fun get() {
        assertMatch(service.get(USER_ID)!!, USER)
    }

    @Test
    fun getNotFound() {
        assertThrows(NotFoundException::class.java) { service.get(1) }
    }

    @Test
    fun getByEmail() {
        assertMatch(service.getByEmail(USER.email!!)!!, USER)
    }

    @Test
    fun getAll() {
        val all = service.getAll()
        assertMatch(all, listOf(ADMIN, USER))
    }

    @Test
    fun update() {
        val updated = User(USER)
        updated.name = "updatedName"
        updated.caloriesPerDay = 310
        service.update(updated)
        assertMatch(service.get(USER_ID)!!, updated)
    }
}