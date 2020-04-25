package com.topjava.service.jpa

import com.topjava.Profiles
import com.topjava.service.AbstractUserServiceTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles(Profiles.JPA)
class JpaUserServiceTest : AbstractUserServiceTest() {
}