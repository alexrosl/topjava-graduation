package com.topjava.model

abstract class AbstractNamedEntity (
        override var id: Int?,
        open val name: String
) : AbstractBaseEntity(id)