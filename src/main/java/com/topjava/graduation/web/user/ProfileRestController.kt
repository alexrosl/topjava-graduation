package com.topjava.graduation.web.user

import com.topjava.graduation.model.User
import com.topjava.graduation.web.SecurityUtil
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ProfileRestController.REST_URL)
class ProfileRestController : AbstractUserController() {

    companion object {
        const val REST_URL = "rest/profile"
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(): User? {
        return super.get(SecurityUtil.authUserId())
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete() {
        super.delete(SecurityUtil.authUserId())
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@RequestBody user: User) {
        super.update(user, SecurityUtil.authUserId())
    }

    @GetMapping("/text")
    fun testUTF(): String {
        return "Русский текст"
    }
}