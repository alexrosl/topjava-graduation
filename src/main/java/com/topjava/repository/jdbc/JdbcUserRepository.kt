package com.topjava.repository.jdbc

import com.topjava.model.User
import com.topjava.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class JdbcUserRepository : UserRepository {
    override fun save(entry: User): User? {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(id: Int): User? {
        TODO("Not yet implemented")
    }

    override fun getByEmail(email: String): User? {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<User> {
        TODO("Not yet implemented")
    }

}