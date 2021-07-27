# Landy Interview Kotlin Test

## Overview

This is a Spring Boot Project, using Java 11, Kotlin, Spring Web, JPA and Security. 

The db is a local instance of H2. 

## About the test

This test contains 4 main tasks. 

Your final target is to make the tests of the project run.

The command to check if the tests are passing is: `./gradlew build`

## 1. Map missing fields on `Person` Entity

Add the following columns to `Person`:
  - `gender: Gender` saved as String on the db
  - `placeOfBirth: City` with a relationship to `City` entity
  - `addressList: List<Address>?` with a relationship of `Many to Many` to `Address` entity

## 2. Create an end point to retrieve person by id

Create a new `@RestController` for `Person`.

This task covers the test `src/test/kotlin/com/landy/service/interview/PersonGetById.kt`.

 - `GET /api/v1/persons/{id}`
    -  returns: Person

Make the test pass before moving to next task.

## 3. Create end points to search by name and get all paged

This task covers the test `PersonGetAllAndSearch.kt`.

It's expected the following end-points:
 - `GET /api/v1/persons` a paged list for all Persons
    - receives: pageable
    - returns: Page< Person >
 - `GET /api/v1/persons?name=William Doe` a list (non-paged) for all Persons
    - receives: name
    - returns: List< Person >
    
## 4. Create end point to create a new person

This task covers the test `PersonPost.kt`.

It's expected the following end-point:
- `POST /api/v1/persons`
    - receives: PersonDto
    - returns: Person

The PersonDto is a simplified version of the Entity:
```kotlin
data class PersonDto(
  val id: Long?,
  val name: String,
  val gender: Gender,
  val placeOfBirthId: Long,
  val addressList: List<AddressDto>?,
)

data class AddressDto(
  val id: Long? = null,

  val street1: String,
  val street2: String?,
  val postCode: String?,
  val cityId: Long,
)
```

## Following properties are used for metrics and monitoring
prometheus and actuator

## Used for CI/CD
Distributed Management
SEM section

## One the service ran successfully
### /ready endpoint useful to identify weather the service is up or not . example like kubernetes pod can be restarted based on the this readiness probe
url: http://localhost:8080/ready

Response:
{"status":"UP","components":{"db":{"status":"UP","details":{"database":"H2","validationQuery":"isValid()"}},"diskSpace":{"status":"UP","details":{"total":499963174912,"free":332074151936,"threshold":10485760,"exists":true}},"ping":{"status":"UP"}}}

