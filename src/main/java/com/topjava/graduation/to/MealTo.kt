package com.topjava.graduation.to

import java.beans.ConstructorProperties
import java.time.LocalDateTime

class MealTo : BaseTo {
    var dateTime: LocalDateTime? = null
    var description: String? = null
    var calories: Int? = null
    var excess: Boolean? = null

    constructor() {}

    @ConstructorProperties("id", "dateTime", "description", "calories", "excess")
    constructor(id: Int, dateTime: LocalDateTime, description: String, calories: Int, excess: Boolean) :
        super(id)
    {
        this.dateTime = dateTime
        this.description = description
        this.calories = calories
        this.excess = excess
    }

    override fun toString(): String {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}'
    }
}