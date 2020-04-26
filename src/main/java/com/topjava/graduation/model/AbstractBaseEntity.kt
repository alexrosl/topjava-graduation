package com.topjava.graduation.model

import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.SequenceGenerator

@MappedSuperclass
@Access(AccessType.FIELD)
abstract class AbstractBaseEntity {
    companion object {
        const val START_SEQ = 100000
    }

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    var id: Int? = null

    constructor() {}
    protected constructor(id: Int?) {
        this.id = id
    }
}

fun AbstractBaseEntity.isNew(): Boolean = this.id == null