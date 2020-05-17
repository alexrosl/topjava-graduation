package com.topjava.graduation.web.user

import com.topjava.graduation.model.User
import com.topjava.graduation.to.UserTo
import com.topjava.graduation.web.SecurityUtil
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value =  [ProfileRestController.REST_URL])
class ProfileRestController : AbstractUserController() {

    companion object {
        const val REST_URL = "/rest/profile"
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

    @PostMapping(value = ["/register"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(value = HttpStatus.CREATED)
    fun register(@Valid @RequestBody userTo: UserTo): ResponseEntity<User> {
        val created = super.create(userTo)!!
        val uriOrNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("$REST_URL/{id}")
                .buildAndExpand(created.id).toUri()
        return ResponseEntity.created(uriOrNewResource).body(created)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@Valid @RequestBody user: User) {
        super.update(user, SecurityUtil.authUserId())
    }

    @GetMapping("/text")
    fun testUTF(): String {
        return "Русский текст"
    }
}