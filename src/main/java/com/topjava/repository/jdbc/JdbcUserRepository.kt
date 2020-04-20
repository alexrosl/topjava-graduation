package com.topjava.repository.jdbc

import com.topjava.model.User
import com.topjava.model.isNew
import com.topjava.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.support.DataAccessUtils
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct

@Repository
class JdbcUserRepository : UserRepository {

    private val ROW_MAPPER = BeanPropertyRowMapper.newInstance(User::class.java)

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate

    private lateinit var insertUser: SimpleJdbcInsert

    @PostConstruct
    private fun init() {
        this.insertUser = SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id")
    }

    override fun save(user: User): User? {
        val parameterSource = BeanPropertySqlParameterSource(user)

        if (user.isNew()) {
            val newKey = insertUser.executeAndReturnKey(parameterSource)
            user.id = newKey.toInt()
        } else {
            //language=PostgreSQL
            if (namedParameterJdbcTemplate.update("""
                UPDATE users SET name=:name, email=:email, password=:password,
                registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay
                WHERE id=:id
            """.trimIndent(), parameterSource) == 0) {
                return null
            }
        }
        return user
    }

    override fun delete(id: Int): Boolean {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0
    }

    override fun get(id: Int): User? {
        val users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id)
        return DataAccessUtils.singleResult(users)
    }

    override fun getByEmail(email: String): User? {
        val users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email)
        return DataAccessUtils.singleResult(users)
    }

    override fun getAll(): List<User> {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER)
    }
}