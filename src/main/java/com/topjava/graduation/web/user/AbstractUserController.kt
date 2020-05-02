package com.topjava.graduation.web.user

import com.topjava.graduation.model.User
import com.topjava.graduation.service.UserService
import com.topjava.graduation.util.ValidationUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

abstract class AbstractUserController {
    companion object {
        internal val logger = LoggerFactory.getLogger(AbstractUserController::class.java)
    }

    @Autowired
    private lateinit var service: UserService

    open fun getAll(): List<User> {
        logger.info("getAll")
        return service.getAll()
    }

    open fun get(id: Int): User? {
        logger.info("get $id")
        return service.get(id)
    }

    fun create(user: User): User? {
        logger.info("create $user")
        ValidationUtil.checkNew(user)
        return service.create(user)
    }

    open fun delete(id: Int) {
        logger.info("delete $id")
        service.delete(id)
    }

    open fun update(user: User, id: Int) {
        logger.info("update $user with id=$id")
        ValidationUtil.assureIdConsistent(user, id)
        service.update(user)
    }

    open fun getByMail(email: String): User? {
        logger.info("getByEmail $email")
        return service.getByEmail(email)
    }

    open fun enable(id: Int, enabled: Boolean) {
        logger.info(if (enabled) "enable {$id}" else "disable {$id}")
        service.enable(id, enabled)
    }
}