package com.topjava.repository.inmemory

import com.topjava.model.User
import com.topjava.repository.UserRepository
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Collectors

@Repository
class InMemoryUserRepository : InMemoryBaseRepository<User>(), UserRepository {

    companion object {
        val USER_ID = 1
        val ADMIN_ID = 2
    }

    override fun getByEmail(email: String): User? {
        return getCollection()
                .firstOrNull { it.email == email }
    }

    override fun getAll(): List<User> {
        return getCollection()
                .sortedWith(compareBy({ it.name }, { it.email }))
    }
}