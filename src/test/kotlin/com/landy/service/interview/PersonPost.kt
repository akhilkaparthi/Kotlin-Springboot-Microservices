package com.landy.service.interview

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post
import org.springframework.web.context.WebApplicationContext

class PersonPost(context: WebApplicationContext) : BaseTest(context) {

  @Test
  fun `Should insert a new person without address`() {
    val person = mapOf(
      "name" to "Suze Orman",
      "gender" to "FEMALE",
      "placeOfBirthId" to 2
    )

    mockMvc.post("/api/v1/persons") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsString(person)
      accept = MediaType.APPLICATION_JSON
      with(adminUser)
    }.andDo {
      print()
    }.andExpect {
      status { isOk() }
      jsonPath("$.id", `is`(notNullValue()))
      jsonPath("$.name", equalTo("Suze Orman"))
      jsonPath("$.gender", equalTo("FEMALE"))
      jsonPath("$.placeOfBirth.name", equalTo("Paris"))
    }
  }

  @Test
  fun `Should insert a new person with address`() {
    val person = mapOf(
      "name" to "Suze Orman",
      "gender" to "FEMALE",
      "placeOfBirthId" to 2,
      "addressList" to listOf(
        mapOf(
          "street1" to "4th Floor, 136",
          "street2" to "Sloane St",
          "postCode" to "SW1X 9AY",
          "cityId" to 1L,
        )
      ),
    )

    mockMvc.post("/api/v1/persons") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsString(person)
      accept = MediaType.APPLICATION_JSON
      with(adminUser)
    }.andDo {
      print()
    }.andExpect {
      status { isOk() }
      jsonPath("$.id", `is`(notNullValue()))
      jsonPath("$.name", equalTo("Suze Orman"))
      jsonPath("$.gender", equalTo("FEMALE"))
      jsonPath("$.placeOfBirth.name", equalTo("Paris"))
      jsonPath("$.addressList", hasSize<Int>(1))
      jsonPath("$.addressList[0].id", `is`(notNullValue()))
      jsonPath("$.addressList[0].street1", equalTo("4th Floor, 136"))
      jsonPath("$.addressList[0].city.name", equalTo("London"))
    }
  }

  @Test
  fun `Should insert a new person and apply jpa auditing`() {
    val person = mapOf(
      "name" to "Suze Orman",
      "gender" to "FEMALE",
      "placeOfBirthId" to 2
    )

    mockMvc.post("/api/v1/persons") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsString(person)
      accept = MediaType.APPLICATION_JSON
      with(adminUser)
    }.andDo {
      print()
    }.andExpect {
      status { isOk() }
      jsonPath("$.id", `is`(notNullValue()))
      jsonPath("$.createdBy", `is`(notNullValue()))
      jsonPath("$.createdDate", `is`(notNullValue()))
      jsonPath("$.lastModifiedBy", `is`(notNullValue()))
      jsonPath("$.lastModifiedDate", `is`(notNullValue()))
    }
  }
}