package com.topjava.graduation.service.jdbc

import com.topjava.graduation.Profiles
import com.topjava.graduation.service.AbstractMealServiceTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles(Profiles.JDBC)
class JdbcMealServiceTest : AbstractMealServiceTest() {
}