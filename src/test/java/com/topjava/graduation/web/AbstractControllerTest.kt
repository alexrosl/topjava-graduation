package com.topjava.graduation.web

import com.topjava.graduation.AllActiveProfileResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import javax.annotation.PostConstruct


@SpringJUnitWebConfig(locations = [
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-mvc.xml",
    "classpath:spring/spring-db.xml"]) //@WebAppConfiguration
//@ExtendWith(SpringExtension.class)
@Transactional
@ActiveProfiles(resolver = AllActiveProfileResolver::class)
abstract class AbstractControllerTest {
    private val CHARACTER_ENCODING_FILTER = CharacterEncodingFilter()

    init {
        CHARACTER_ENCODING_FILTER.encoding = "UTF-8"
        CHARACTER_ENCODING_FILTER.setForceEncoding(true)
    }

    protected lateinit var mockMvc: MockMvc;

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @PostConstruct
    private fun postContstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter<DefaultMockMvcBuilder>(CHARACTER_ENCODING_FILTER)
                .build()
    }
}