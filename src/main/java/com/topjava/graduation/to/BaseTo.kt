package com.topjava.graduation.to

import org.springframework.util.Assert

abstract class BaseTo {
    var id: Int? = null

    constructor() {}
    protected constructor(id: Int?) {
        this.id = id
    }

    open fun id(): Int {
        Assert.notNull(id, "Entity must has id")
        return id!!
    }
}