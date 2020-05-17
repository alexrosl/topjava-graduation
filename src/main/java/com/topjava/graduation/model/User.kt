package com.topjava.graduation.model

import com.topjava.graduation.util.MealsUtil.DEFAULT_CALORIES_PER_DAY
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.validator.constraints.Range
import org.springframework.util.CollectionUtils
import java.util.*
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.OneToMany
import javax.persistence.OrderBy
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries(
        NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id = :id"),
        NamedQuery(name = User.BY_EMAIL, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=?1"),
        NamedQuery(name = User.ALL_SORTED, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email")
)
@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["email"], name = "users_unique_email_idx")])
class User : AbstractNamedEntity {
    companion object {
        const val DELETE = "User.delete"
        const val BY_EMAIL = "User.getByEmail"
        const val ALL_SORTED = "User.getAllSorted"
    }

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    var email: String? = null

    @Column(name = "password")
    @NotBlank
    @Size(min = 5, max = 100)
    var password: String? = null

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    var enabled = true

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    var registered = Date()

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    var roles: MutableSet<Role>? = null

    @Column(name = "calories_per_day", nullable = false, columnDefinition = "int default 2000")
    @Range(min = 10, max = 10000)
    var caloriesPerDay = DEFAULT_CALORIES_PER_DAY

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("dateTime DESC")
    var meals: MutableList<Meal>? = null

    constructor() {}
    constructor(u: User) : this(u.id, u.name, u.email, u.password, u.caloriesPerDay, u.enabled, u.registered, u.roles) {}
    constructor(id: Int?, name: String?, email: String?, password: String?, role: Role?, vararg roles: Role?) : this(id, name, email, password, DEFAULT_CALORIES_PER_DAY, true, Date(), EnumSet.of(role, *roles))
    constructor(id: Int?, name: String?, email: String?, password: String?, caloriesPerDay: Int?, role: Role?, vararg  roles: Role?) : this(id, name, email, password, caloriesPerDay!!, true, Date(), EnumSet.of(role, *roles))
    constructor(id: Int?, name: String?, email: String?, password: String?, caloriesPerDay: Int, enabled: Boolean, registered: Date, roles: Collection<Role>?) : super(id, name) {
        this.email = email
        this.password = password
        this.caloriesPerDay = caloriesPerDay
        this.enabled = enabled
        this.registered = registered
        setRoles(roles)
    }

    fun setRoles(roles: Collection<Role>?) {
        this.roles = if (CollectionUtils.isEmpty(roles)) EnumSet.noneOf(Role::class.java) else EnumSet.copyOf(roles)
    }

    override fun toString(): String {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", caloriesPerDay=" + caloriesPerDay +
                '}'
    }
}