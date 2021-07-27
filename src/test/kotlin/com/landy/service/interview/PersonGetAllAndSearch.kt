package com.landy.service.interview

import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.web.context.WebApplicationContext

class PersonGetAllAndSearch(context: WebApplicationContext): BaseTest(context) {

  @Test
  fun `Should list all users`() {
    mockMvc.get("/api/v1/persons?sort=id,asc") {
      accept = MediaType.APPLICATION_JSON
      with(adminUser)
    }.andDo {
      print()
    }.andExpect {
      status { isOk() }
      jsonPath("$.content", hasSize<Int>(2))
      jsonPath("$.content[0].name", Matchers.equalTo("Benjamin Graham"))
      jsonPath("$.content[0].gender", Matchers.equalTo("MALE"))
      jsonPath("$.content[0].placeOfBirth.name", Matchers.equalTo("London"))

      jsonPath("$.content[1].name", Matchers.equalTo("Warren Buffett"))
      jsonPath("$.content[1].gender", Matchers.equalTo("MALE"))
      jsonPath("$.content[1].placeOfBirth.name", Matchers.equalTo("Paris"))
    }
  }

  @Test
  fun `Should search user by name`() {
    mockMvc.get("/api/v1/persons/?name=Graham") {
      accept = MediaType.APPLICATION_JSON
      with(adminUser)
    }.andDo {
      print()
    }.andExpect {
      status { isOk() }
      jsonPath("$", hasSize<Int>(1))
      jsonPath("$[0].name", Matchers.equalTo("Benjamin Graham"))
    }
  }
}
