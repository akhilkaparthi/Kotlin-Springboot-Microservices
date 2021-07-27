package com.landy.service.interview

import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.web.context.WebApplicationContext

class PersonGetById(context: WebApplicationContext): BaseTest(context) {

  @Test
  fun `Should list one user by id`() {
    mockMvc.get("/api/v1/persons/2") {
      accept = MediaType.APPLICATION_JSON
      with(adminUser)
    }.andDo {
      print()
    }.andExpect {
      status { isOk() }
      jsonPath("$.id", Matchers.equalTo(2))
      jsonPath("$.name", Matchers.equalTo("Warren Buffett"))
      jsonPath("$.gender", Matchers.equalTo("MALE"))
      jsonPath("$.placeOfBirth.name", Matchers.equalTo("Paris"))
    }
  }

  @Test
  fun `Should validate when user not found by id`() {
    val ex = assertThrows<Exception> {
      mockMvc.get("/api/v1/persons/10000") {
        accept = MediaType.APPLICATION_JSON
        with(adminUser)
      }
    }

    assertThat(ex.message).containsIgnoringCase("not found person")
  }
}
