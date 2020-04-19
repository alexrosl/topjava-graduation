package com.topjava.web.user

import com.topjava.model.User
import com.topjava.web.SecurityUtil

class ProfileRestController : AbstractUserController() {
    fun get(): User? {
        return super.get(SecurityUtil.authUserId())
    }

    fun delete() {
        super.delete(SecurityUtil.authUserId())
    }

    fun update(user: User) {
        super.update(user, SecurityUtil.authUserId())
    }
}