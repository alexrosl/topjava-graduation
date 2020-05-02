package com.topjava.graduation.repository.datajpa

import com.topjava.graduation.model.User
import com.topjava.graduation.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository

@Repository
class DataJpaUserRepository : UserRepository {
    companion object {
        private val SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email")
    }

    @Autowired
    private lateinit var crudUserRepository: CrudUserRepository

    override fun save(entry: User): User? {
        return crudUserRepository.save(entry)
    }

    override fun delete(id: Int): Boolean {
        return crudUserRepository.delete(id) != 0
    }

    override fun get(id: Int): User? {
        return crudUserRepository.findById(id).orElse(null)
    }

    override fun getByEmail(email: String): User? {
        return crudUserRepository.getByEmail(email)
    }

    override fun getAll(): List<User> {
        return crudUserRepository.findAll(SORT_NAME_EMAIL)
    }

    override fun getWithMeals(id: Int): User? {
        return crudUserRepository.getWithMeals(id)
    }
}