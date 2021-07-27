package com.landy.service.interview.controller

import com.google.gson.Gson
import com.landy.service.interview.entities.Person
import com.landy.service.interview.entities.PersonDto
import com.landy.service.interview.service.PersonService
import org.hibernate.exception.ConstraintViolationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import javax.servlet.http.HttpServletResponse


/**
 * The REST API for the Contact Service
 */
@RestController
@RequestMapping("/api/v1")
@ControllerAdvice
private class PersonController(@Autowired private val personService: PersonService) {
    @ExceptionHandler(
        ConstraintViolationException::class,
        HttpClientErrorException.BadRequest::class,
        MethodArgumentNotValidException::class,
        MissingServletRequestParameterException::class,
        IllegalArgumentException::class
    )

    /**
     * REST API to obtain person details by id
     *
     * @return a person as JSON
     */

    @GetMapping("/persons/{id}")
    fun getPersonsById(@PathVariable("id") personId: Long, e: Exception): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(personService.getPersonById(personId))
        } catch (e: Exception) {
            throw Exception("not found person");
        }

    }

    /**
     * REST API to obtain all persons
     *
     * @return all of the persons as JSON
     */
    @GetMapping("/persons")
    fun findAllOrderByNameAsc(@RequestParam params: HashMap<String, String>, response: HttpServletResponse) {
        if (params.containsKey("name")) {
            val personList: List<Person>? = personService.findByName(params.getValue("name"))
            val gson = Gson()
            var jsonString = gson.toJson(personList)
            contentType(response, jsonString)

        } else if (params.containsKey("sort")) {
            val sortArr = params.getValue("sort").split(",").toTypedArray()
            val sortType = sortArr[0]
            val personList: List<Person>? = personService.findAllOrderByNameAsc(0, 2, sortType)
            val hashMap: HashMap<String, List<Person>> = HashMap()
            if (personList != null) {
                hashMap["content"] = personList
            }
            val gson = Gson()
            var jsonString = gson.toJson(hashMap)
            contentType(response, jsonString)
            response.writer.write(jsonString)
        }
    }

    private fun contentType(response: HttpServletResponse, jsonString: String?) {
        response.contentType = "text/plain" // Set content type of the response so that jQuery knows what it can expect.
        response.characterEncoding = "UTF-8" // You want world domination, huh?
        response.writer.write(jsonString) // Write response body.
    }

    /**
     * REST API to save new person details
     *
     * @return person details
     */
    @PostMapping("/persons")
    fun createPerson(@RequestBody payload: PersonDto?, response: HttpServletResponse) {
        val personDto: PersonDto? = payload

        try {
            if (personDto != null) {
                val person: Person? = personService.createNewPerson(personDto)
                val gson = Gson()
                var jsonString = gson.toJson(person)
                contentType(response, jsonString)
            };

        } catch (e: Exception) {
            response.status
            throw e;
        }

    }
}