package com.topjava.graduation.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RootController {
    @GetMapping("/login")
    fun login(): String {
        return """
            <form action="spring_security_check" method="post">
            <input type="text" placeholder="Email" name="username">
            <input type="password" placeholder="Password" name="password">
            <button  type="submit"> Submit
            </button>
        </form>
        """.trimIndent()
    }

    @GetMapping("/")
    fun root(): String {
        return "main page"
    }
}