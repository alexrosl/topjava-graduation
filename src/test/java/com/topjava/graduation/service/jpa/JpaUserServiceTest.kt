package com.topjava.graduation.service.jpa

import com.topjava.graduation.Profiles
import com.topjava.graduation.service.AbstractUserServiceTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles(Profiles.JPA)
class JpaUserServiceTest : AbstractUserServiceTest() {
}