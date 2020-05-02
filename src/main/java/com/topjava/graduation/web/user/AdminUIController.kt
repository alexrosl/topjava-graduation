package com.topjava.graduation.web.user

import com.topjava.graduation.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ajax/admin/users")
class AdminUIController : AbstractUserController() {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun getAll(): List<User> {
        return super.getAll()
    }

    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun get(@PathVariable id: Int): User? {
        return super.get(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    override fun delete(@PathVariable id: Int) {
        super.delete(id)
    }

    @PostMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    override fun enable(@PathVariable id: Int, @RequestParam enabled: Boolean) {
        super.enable(id, enabled)
    }
}