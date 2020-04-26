package com.topjava.graduation.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.validator.constraints.Range
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@NamedQueries(
        NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC"),
        NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId"),
        NamedQuery(name = Meal.GET_BETWEEN, query = "SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >= :startDate AND m.dateTime < :endDate ORDER BY m.dateTime DESC"))
@Entity
@Table(name = "meals", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "date_time"], name = "meals_unique_user_datetime_idx")])
class Meal : AbstractBaseEntity {
    companion object {
        const val ALL_SORTED = "Meal.getAll"
        const val DELETE = "Meal.delete"
        const val GET_BETWEEN = "Meal.getBetween"
    }

    @Column(name = "date_time", nullable = false)
    @NotNull
    var dateTime: LocalDateTime? = null

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    var description: String? = null

    @Column(name = "calories", nullable = false)
    @Range(min = 10, max = 5000)
    var calories = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    var user: User? = null

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