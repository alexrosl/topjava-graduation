package com.topjava.graduation.web.user

import com.topjava.graduation.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping(value = [AdminRestController.REST_URL], produces = [MediaType.APPLICATION_JSON_VALUE])
class AdminRestController : AbstractUserController() {

    companion object {
        const val REST_URL = "/rest/admin/users"
    }

    @GetMapping
    override fun getAll(): List<User> {
        return super.getAll()
    }

    @GetMapping("/{id}")
    override fun get(@PathVariable id: Int): User? {
        return super.get(id)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createWithLocation(@RequestBody user: User): ResponseEntity<User> {
        val created = super.create(user)
        val uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("$REST_URL/{id}")
                .buildAndExpand(created!!.id).toUri()
        return ResponseEntity.created(uriOfNewResource).body(created)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun delete(@PathVariable id: Int) {
        super.delete(id)
    }

    @PutMapping(value = ["/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    override fun update(@RequestBody user: User, @PathVariable id: Int) {
        super.update(user, id)
    }

    @GetMapping("/by")
    override fun getByMail(@RequestParam email: String): User? {
        return super.getByMail(email)
    }

    @PatchMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    override fun enable(@PathVariable id: Int, @RequestParam enabled: Boolean) {
        super.enable(id, enabled)
    }
}