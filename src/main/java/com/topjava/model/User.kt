package com.topjava.model

import com.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY
import org.springframework.util.CollectionUtils
import java.util.*

class User : AbstractNamedEntity {
    var email: String? = null
    var password: String? = null
    var isEnabled = true
    var registered = Date()
    private var roles: Set<Role?>? = null
    var caloriesPerDay = DEFAULT_CALORIES_PER_DAY

    constructor() {}
    constructor(u: User) : this(u.id, u.name, u.email, u.password, u.caloriesPerDay, u.isEnabled, u.registered, u.roles) {}
    constructor(id: Int?, name: String?, email: String?, password: String?, role: Role?, vararg roles: Role?) : this(id, name, email, password, DEFAULT_CALORIES_PER_DAY, true, Date(), EnumSet.of(role, *roles)) {}
    constructor(id: Int?, name: String?, email: String?, password: String?, caloriesPerDay: Int, enabled: Boolean, registered: Date, roles: Collection<Role?>?) : super(id, name) {
        this.email = email
        this.password = password
        this.caloriesPerDay = caloriesPerDay
        isEnabled = enabled
        this.registered = registered
        setRoles(roles)
    }

    private fun setRoles(roles: Collection<Role?>?) {
        this.roles = if (CollectionUtils.isEmpty(roles)) EnumSet.noneOf(Role::class.java) else EnumSet.copyOf(roles)
    }

    override fun toString(): String {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + isEnabled +
                ", roles=" + roles +
                ", caloriesPerDay=" + caloriesPerDay +
                '}'
    }
}