package com.topjava.model

abstract class AbstractBaseEntity (
    open var id: Int?
)

fun AbstractBaseEntity.isNew(): Boolean = this.id == null