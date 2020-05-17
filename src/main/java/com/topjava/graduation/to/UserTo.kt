package com.topjava.graduation.to

import com.topjava.graduation.util.UserUtil
import org.hibernate.validator.constraints.Range
import java.io.Serializable
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class UserTo : BaseTo, Serializable {
    companion object {
        private val serialVersionUID = 1L
    }

    @NotBlank
    @Size(min = 2, max = 100)
    var name: String? = null

    @Email
    @NotBlank
    @Size(max = 100)
    var email: String? = null

    @NotBlank
    @Size(min = 5, max = 32, message = "length must be between 5 and 32 characters")
    var password: String? = null

    @Range(min = 10, max = 10000)
    var caloriesPerDay: Int? = UserUtil.DEFAULT_CALORIES_PER_DAY

    constructor() {}

    constructor(id: Int?, name: String?, email: String?, password: String?, caloriesPerDay: Int?) :
        super(id)
    {
        this.name = name
        this.email = email
        this.password = password
        this.caloriesPerDay = caloriesPerDay
    }
}