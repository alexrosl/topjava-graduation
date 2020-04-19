package com.topjava.model

abstract class AbstractNamedEntity : AbstractBaseEntity {
    var name: String? = null

    constructor() {}
    protected constructor(id: Int?, name: String?) : super(id) {
        this.name = name
    }
}