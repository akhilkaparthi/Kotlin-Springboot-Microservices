package com.landy.service.interview.service

import com.landy.service.interview.entities.Address
import com.landy.service.interview.entities.City
import com.landy.service.interview.entities.Person
import com.landy.service.interview.entities.PersonDto
import com.landy.service.interview.repository.AddressRepository
import com.landy.service.interview.repository.CityRepository
import com.landy.service.interview.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*


/**
 * The concrete implementation of the person service
 */
@Service
class PersonService(
    @Autowired private val personRepository: PersonRepository,
    @Autowired private val cityRepository: CityRepository,
    @Autowired private val addressReposity: AddressRepository
) {
    /**
     * This method is used to get person details by id
     */
    fun getPersonById(personId: Long): Person = personRepository.findById(personId)
        .orElseThrow { Exception() }

    /**
     * This methdos is used get pageable list of all persons
     */
    fun findAllOrderByNameAsc(pageNo: Int?, pageSize: Int?, sortBy: String?): List<Person>? {
        val paging: Pageable = PageRequest.of(pageNo!!, pageSize!!, Sort.by(sortBy).ascending())
        val pagedResult: Page<Person> = personRepository.findAll(paging)
        return if (pagedResult.hasContent()) {
            pagedResult.content
        } else {
            ArrayList<Person>()
        }
    }

    /**
     * This method is used to get all persons like name
     */
    fun findByName(name: String): List<Person>? {
        return if(!name.isNullOrEmpty()){
            personRepository.findByName(name)
        }else{
            emptyList()
        }
    }

    /**
     * This method is used to create a new person
     */
    fun createNewPerson(personDto: PersonDto): Person? {
        try {
            val city: Optional<City> = getCity(personDto.placeOfBirthId)
            val addressList = arrayListOf<Address>()
            if (personDto.addressList != null) {
                for (addressDto in personDto.addressList) {
                    val city: Optional<City> = getCity(addressDto.cityId)
                    val address = Address(null, addressDto.street1, addressDto.street2, addressDto.postCode, city.get())
                    addressList.add(addressReposity.save(address))
                }
            }
            val date: Date = Date()
            val person = Person(
                null,
                "user",
                date.toInstant(),
                "user",
                date.toInstant(),
                personDto.name,
                personDto.gender.name,
                city.get(),
                addressList
            )
            return personRepository.save(person)

        } catch (e: Exception) {
            throw e
        }

    }

    /**
     * This method is used get city details by id
     */
    private fun getCity(cityId: Long): Optional<City> {
        val city: Optional<City> = cityRepository.findById(cityId);
        return city
    }

}