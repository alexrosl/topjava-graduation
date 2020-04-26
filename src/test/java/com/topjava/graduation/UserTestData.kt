package com.topjava.graduation

import com.topjava.graduation.model.AbstractBaseEntity
import com.topjava.graduation.model.Role
import com.topjava.graduation.model.User
import org.assertj.core.api.Assertions

object UserTestData {
    val USER_ID = AbstractBaseEntity.START_SEQ
    val ADMIN_ID = AbstractBaseEntity.START_SEQ + 1

    val USER = User(USER_ID, "user", "email@mail.ru", "password", Role.ROLE_USER)
    val ADMIN = User(ADMIN_ID, "admin", "admin@mail.ru", "admin", Role.ROLE_ADMIN)

    fun assertMatch(actual: User, expected: User) {
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles", "meals")
    }

    fun assertMatch(actual: Iterable<User>, expected: Iterable<User?>?) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles", "meals").isEqualTo(expected)
    }
}