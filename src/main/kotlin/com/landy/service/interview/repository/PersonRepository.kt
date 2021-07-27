package com.landy.service.interview.repository

import com.landy.service.interview.entities.City
import com.landy.service.interview.entities.Person
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface PersonRepository : JpaRepository<Person, Long> {
    @Query(value = "SELECT * FROM PERSON WHERE name Like %?1%", nativeQuery = true)
    fun findByName(name: String): List<Person>?

}
