package com.topjava.graduation.service

import com.topjava.graduation.model.User
import com.topjava.graduation.repository.UserRepository
import com.topjava.graduation.util.NotFoundException
import com.topjava.graduation.util.ValidationUtil.checkNotFound
import com.topjava.graduation.util.ValidationUtil.checkNotFoundWithId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Service
class UserService {

    @Autowired
    private lateinit var repository: UserRepository

    fun create(user: User): User? {
        Assert.notNull(user, "user must not be null")
        return repository.save(user)
    }

    @Throws(NotFoundException::class)
    fun delete(id: Int) {
        checkNotFoundWithId(repository.delete(id), id)
    }

    @Throws(NotFoundException::class)
    fun get(id: Int): User? {
        return checkNotFoundWithId(repository.get(id), id)
    }

    @Throws(NotFoundException::class)
    fun getByEmail(email: String): User? {
        Assert.notNull(email, "email must not be null")
        return checkNotFound(repository.getByEmail(email), "email=$email")
    }

    fun getAll(): List<User> {
        return repository.getAll()
    }

    fun update(user: User) {
        Assert.notNull(user, "user must not be null")
        checkNotFoundWithId(repository.save(user), user.id!!)
    }

    fun getWithMeals(id: Int): User? {
        return checkNotFoundWithId(repository.getWithMeals(id), id)
    }
}