package com.topjava.graduation

import java.lang.IllegalStateException

object Profiles {
    const val JDBC = "jdbc"
    const val JPA = "jpa"
    const val DATAJPA = "datajpa"

    const val REPOSITORY_IMPLEMENTATION = DATAJPA

    const val POSTGRES_DB = "postgres"

    fun getActiveDbProfile(): String {
        try {
            Class.forName("org.postgresql.Driver")
            return POSTGRES_DB
        } catch (ex: ClassNotFoundException) {
            throw IllegalStateException("Could not find DB driver")
        }
    }
}