package com.topjava.service.jdbc

import com.topjava.Profiles
import com.topjava.service.AbstractMealServiceTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles(Profiles.JDBC)
class JdbcMealServiceTest : AbstractMealServiceTest() {
}