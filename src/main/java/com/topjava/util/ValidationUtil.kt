package com.topjava.util

import com.topjava.model.AbstractBaseEntity
import com.topjava.model.isNew

object ValidationUtil {
    fun <T> checkNotFoundWithId(`object`: T, id: Int): T {
        return checkNotFound(`object`, "id=$id")
    }

    fun checkNotFoundWithId(found: Boolean, id: Int) {
        checkNotFound(found, "id=$id")
    }

    fun <T> checkNotFound(`object`: T, msg: String) : T {
        checkNotFound(`object` != null, msg)
        return `object`
    }

    fun checkNotFound(found: Boolean, msg: String) {
        if (!found) {
            throw NotFoundException("Not found entity with $msg")
        }
    }

    fun checkNew(entity: AbstractBaseEntity) {
        if (!entity.isNew()) {
            throw IllegalArgumentException("$entity msut be new (id=null)")
        }
    }

    fun assureIdConsistent(entity: AbstractBaseEntity, id: Int) {
        if (entity.isNew()) {
            entity.id = id
        } else if (entity.id != id) {
            throw IllegalArgumentException("$entity must be with id=$id")
        }
    }
}

class NotFoundException(message: String) : RuntimeException(message)