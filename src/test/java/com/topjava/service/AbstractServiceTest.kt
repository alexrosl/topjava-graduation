package com.topjava.service

import com.topjava.ActiveDbProfileResolver
import com.topjava.Profiles
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(locations = [
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-db.xml"]
) //@ExtendWith(SpringExtension.class)
@Sql(scripts = ["classpath:db/populateDB.sql"], config = SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = ActiveDbProfileResolver::class)
abstract class AbstractServiceTest {
}