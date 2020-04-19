package com.topjava.web.user

import com.topjava.model.User
import com.topjava.service.UserService
import com.topjava.util.ValidationUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

abstract class AbstractUserController {
    companion object {
        internal val logger = LoggerFactory.getLogger(AbstractUserController::class.java)
    }

    @Autowired
    private lateinit var service: UserService

    fun getAll(): List<User> {
        logger.info("getAll")
        return service.getAll()
    }

    fun get(id: Int): User? {
        logger.info("get $id")
        return service.get(id)
    }

    fun create(user: User): User? {
        logger.info("create $user")
        ValidationUtil.checkNew(user)
        return service.create(user)
    }

    fun delete(id: Int) {
        logger.info("delete $id")
        service.delete(id)
    }

    fun update(user: User, id: Int) {
        logger.info("update $user with id=$id")
        ValidationUtil.assureIdConsistent(user, id)
        service.update(user)
    }

    fun getByMail(email: String): User? {
        logger.info("getByEmail $email")
        return service.getByEmail(email)
    }
}