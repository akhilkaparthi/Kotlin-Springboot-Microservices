package com.landy.service.interview

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
abstract class BaseTest(
  var context: WebApplicationContext,
) {

  @Autowired
  lateinit var objectMapper: ObjectMapper

  @Autowired
  lateinit var jdbcTemplate: JdbcTemplate

  lateinit var mockMvc: MockMvc

  @BeforeEach
  fun setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply<DefaultMockMvcBuilder>(springSecurity()).build()

    println("LANDY TEST: Cleaning the data")
    jdbcTemplate.execute("delete from PERSON_ADDRESS_LIST")
    jdbcTemplate.execute("delete from ADDRESS")
    jdbcTemplate.execute("delete from PERSON")
    jdbcTemplate.execute("delete from CITY")

    println("LANDY TEST: Preparing test data")
    jdbcTemplate.execute("insert into city (id, name) values(1, 'London')")
    jdbcTemplate.execute("insert into city (id, name) values(2, 'Paris')")

    jdbcTemplate.execute(
      """insert into person (id, created_by, created_date, last_modified_by, last_modified_date, name, gender, place_of_birth_id) 
      |values(1, 'sys', current_timestamp, 'sys', current_timestamp, 'Benjamin Graham', 'MALE', 1)""".trimMargin()
    )

    jdbcTemplate.execute(
      """insert into person (id, created_by, created_date, last_modified_by, last_modified_date, name, gender, place_of_birth_id) 
      |values(2, 'sys', current_timestamp, 'sys', current_timestamp, 'Warren Buffett', 'MALE', 2)""".trimMargin()
    )
  }

  companion object {
    val adminUser = SecurityMockMvcRequestPostProcessors.user("admin").password("pass").roles("USER", "ADMIN")
  }
}
