package com.topjava.graduation.util

import com.topjava.graduation.model.AbstractBaseEntity
import com.topjava.graduation.model.isNew
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult

import javax.validation.*


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

    private var validator: Validator

    init {
        val factory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    fun <T> validate(bean: T) {
        val violations = validator.validate(bean)
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }
    }

    fun getErrorResponse(result: BindingResult): ResponseEntity<String> {
        return ResponseEntity.unprocessableEntity().body(
                result.fieldErrors
                        .joinToString(separator = "<br>") { "[${it.field}] ${it.defaultMessage}" }
        )
    }
}

class NotFoundException(message: String) : RuntimeException(message)