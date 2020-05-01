package com.topjava.graduation

import org.springframework.test.context.ActiveProfilesResolver

class AllActiveProfileResolver : ActiveProfilesResolver {
    override fun resolve(testClass: Class<*>): Array<String> {
        return arrayOf(Profiles.REPOSITORY_IMPLEMENTATION, Profiles.getActiveDbProfile())
    }
}