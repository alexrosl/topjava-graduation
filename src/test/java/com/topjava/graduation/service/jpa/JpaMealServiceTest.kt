package com.topjava.graduation.service.jpa

import com.topjava.graduation.Profiles
import com.topjava.graduation.service.AbstractMealServiceTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles(Profiles.JPA)
class JpaMealServiceTest: AbstractMealServiceTest() {
}