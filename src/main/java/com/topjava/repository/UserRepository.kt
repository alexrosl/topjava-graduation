package com.topjava.repository

import com.topjava.model.User

interface UserRepository {
    fun save(entry: User): User?
    fun delete(id: Int): Boolean
    fun get(id: Int): User?
    fun getByEmail(email: String): User?
    fun getAll(): List<User>
}