package com.topjava

import org.springframework.test.context.ActiveProfilesResolver

class ActiveDbProfileResolver : ActiveProfilesResolver {
    override fun resolve(testClass: Class<*>): Array<String> {
        return arrayOf(Profiles.getActiveDbProfile())
    }
}