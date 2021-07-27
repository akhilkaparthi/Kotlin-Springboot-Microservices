package com.landy.service.interview.entities

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.*

@Entity
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @CreatedBy var createdBy: String? = null,
    @CreatedDate var createdDate: Instant? = null,
    @LastModifiedBy var lastModifiedBy: String? = null,
    @LastModifiedDate var lastModifiedDate: Instant? = null,
    var name: String,
    var gender: String? = null,

    @ManyToOne var placeOfBirth: City,

    @ManyToMany
    var addressList: List<Address>,

    ) {
    override fun toString(): String {
        return "Person(id=$id, createdBy=$createdBy, createdDate=$createdDate, lastModifiedBy=$lastModifiedBy, lastModifiedDate=$lastModifiedDate, name='$name', gender=$gender, placeOfBirth=$placeOfBirth, addressList=$addressList)"
    }
}

data class PersonDto(
    val id: Long?,
    val name: String,
    val gender: Gender,
    val placeOfBirthId: Long,
    val addressList: List<AddressDto>?,
)

//fun PersonDto.toEntity(city:City,addressList: List<Address>) = Person(
//  id = this.id,
//  name = this.name,
//  gender = this.gender.name,
//  addressList = addressList,
//  placeOfBirth = city
//)


