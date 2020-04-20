package com.topjava.model

import java.time.LocalDateTime

class Meal : AbstractBaseEntity {
    var dateTime: LocalDateTime? = null
    var description: String? = null
    var calories = 0

    constructor() {}
    constructor(dateTime: LocalDateTime?, description: String?, calories: Int) : this(null, dateTime, description, calories) {}
    constructor(id: Int?, dateTime: LocalDateTime?, description: String?, calories: Int) : super(id) {
        this.dateTime = dateTime
        this.description = description
        this.calories = calories
    }

    override fun toString(): String {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}'
    }
}