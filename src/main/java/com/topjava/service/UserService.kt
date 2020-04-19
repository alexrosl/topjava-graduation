package com.topjava.service

import com.topjava.model.User
import com.topjava.repository.UserRepository
import com.topjava.util.NotFoundException
import com.topjava.util.ValidationUtil.checkNotFound
import com.topjava.util.ValidationUtil.checkNotFoundWithId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private lateinit var repository: UserRepository

    fun create(user: User): User? {
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
        return checkNotFound(repository.getByEmail(email), "email=$email")
    }

    fun getAll(): List<User> {
        return repository.getAll()
    }

    fun update(user: User) {
        checkNotFoundWithId(repository.save(user), user.id!!)
    }
}