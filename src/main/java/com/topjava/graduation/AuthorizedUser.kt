package com.topjava.graduation

import com.topjava.graduation.model.User
import com.topjava.graduation.to.UserTo
import com.topjava.graduation.util.UserUtil
import com.topjava.graduation.util.UserUtil.asTo

class AuthorizedUser(user: User) : org.springframework.security.core.userdetails.User(user.email, user.password, user.enabled, true, true, true, user.roles) {
    companion object {
        private const val serialVersionUID = 1L
    }

    private var userTo: UserTo

    init {
        this.userTo = asTo(user)
    }

    fun update(newTo: UserTo) {
        userTo = newTo
    }

    fun getId(): Int {
        return userTo.id!!
    }

}