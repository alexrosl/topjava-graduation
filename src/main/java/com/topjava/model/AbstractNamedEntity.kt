package com.topjava.model

import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@MappedSuperclass
abstract class AbstractNamedEntity : AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    var name: String? = null

    constructor() {}
    protected constructor(id: Int?, name: String?) : super(id) {
        this.name = name
    }
}