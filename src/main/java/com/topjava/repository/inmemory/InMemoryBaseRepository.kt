package com.topjava.repository.inmemory

import com.topjava.model.AbstractBaseEntity
import com.topjava.model.User
import com.topjava.model.isNew
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Repository
open class InMemoryBaseRepository<T : AbstractBaseEntity> {
    private val counter = AtomicInteger(0)
    private var map: MutableMap<Int, T> = ConcurrentHashMap()

    open fun save(entry: T): T {
        if (entry.isNew()) {
            entry.id = counter.incrementAndGet()
            map[entry.id!!] = entry
            return entry
        }
        return map.computeIfPresent(entry.id!!) { _: Int?, _: T? -> entry}!!
    }

    open fun delete(id: Int): Boolean {
        return map.remove(id) != null
    }

    open fun get(id: Int): T? {
        return map[id]
    }

    fun getCollection(): Collection<T> {
        return map.values
    }
}