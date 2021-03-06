package com.topjava.graduation.repository.jpa

import com.topjava.graduation.model.User
import com.topjava.graduation.model.isNew
import com.topjava.graduation.repository.UserRepository
import org.hibernate.jpa.QueryHints
import org.springframework.dao.support.DataAccessUtils
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional(readOnly = true)
open class JpaUserRepository : UserRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    @Transactional
    override fun save(entry: User): User? {
        return if (entry.isNew()) {
            em.persist(entry)
            entry
        } else {
            em.merge(entry)
        }
    }

    @Transactional
    override fun delete(id: Int): Boolean {
        return em.createNamedQuery(User.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0
    }

    override fun get(id: Int): User? {
        return em.find(User::class.java, id)
    }

    override fun getByEmail(email: String): User? {
        val resultList = em.createNamedQuery(User.BY_EMAIL, User::class.java)
                .setParameter(1, email)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .resultList
        return DataAccessUtils.singleResult(resultList)
    }

    override fun getAll(): List<User> {
        return em.createNamedQuery(User.ALL_SORTED, User::class.java).resultList
    }


}