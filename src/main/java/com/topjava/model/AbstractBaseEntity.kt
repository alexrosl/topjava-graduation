package com.topjava.model

abstract class AbstractBaseEntity {
    companion object {
        const val START_SEQ = 100000
    }

    var id: Int? = null

    constructor() {}
    protected constructor(id: Int?) {
        this.id = id
    }
}

fun AbstractBaseEntity.isNew(): Boolean = this.id == null