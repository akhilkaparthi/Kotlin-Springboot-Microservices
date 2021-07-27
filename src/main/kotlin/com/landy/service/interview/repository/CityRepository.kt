package com.landy.service.interview.repository

import com.landy.service.interview.entities.City
import com.landy.service.interview.entities.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CityRepository : JpaRepository<City, Long> {
}