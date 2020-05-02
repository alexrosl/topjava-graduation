package com.topjava.graduation.repository.jdbc

import com.topjava.graduation.model.Role
import com.topjava.graduation.model.User
import com.topjava.graduation.model.isNew
import com.topjava.graduation.repository.UserRepository
import com.topjava.graduation.util.ValidationUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.support.DataAccessUtils
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.PreparedStatement
import java.util.*
import java.util.function.Function
import javax.annotation.PostConstruct

@Repository
@Transactional(readOnly = true)
open class JdbcUserRepository : UserRepository {

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

    @Transactional
    override fun save(user: User): User? {
        ValidationUtil.validate(user)

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
            deleteRoles(user)
            insertRoles(user)
        }
        return user
    }

    @Transactional
    override fun delete(id: Int): Boolean {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0
    }

    override fun get(id: Int): User? {
        val users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id)
        return setRoles(DataAccessUtils.singleResult(users))
    }

    override fun getByEmail(email: String): User? {
        val users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email)
        return setRoles(DataAccessUtils.singleResult(users))
    }

    override fun getAll(): List<User> {
        val map = mutableMapOf<Int, MutableSet<Role>>()
        jdbcTemplate.query("SELECT * FROM user_roles") {
            rs ->
            map.computeIfAbsent(rs.getInt("user_id")) { EnumSet.noneOf(Role::class.java) }
                    .add(Role.valueOf(rs.getString("role")))
        }

        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER)
    }

    private fun insertRoles(u: User?) {
        val roles = u?.roles
        if (!roles.isNullOrEmpty()) {
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)", roles, roles.size
            ) { ps: PreparedStatement, role: Role ->
                ps.setInt(1, u.id!!)
                ps.setString(2, role.name)
            }
        }
    }

    private fun deleteRoles(u: User?) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", u?.id)
    }

    private fun setRoles(u: User?): User? {
        if (u != null) {
            val roles = jdbcTemplate.queryForList("SELECT role FROM user_roles WHERE user_id=?", Role::class.java, u.id)
            u.setRoles(roles)
        }
        return u
    }
}