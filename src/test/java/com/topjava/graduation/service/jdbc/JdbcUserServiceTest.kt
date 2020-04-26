package com.topjava.graduation.service.jdbc

import com.topjava.graduation.Profiles
import com.topjava.graduation.service.AbstractUserServiceTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles(Profiles.JDBC)
class JdbcUserServiceTest : AbstractUserServiceTest() {

}