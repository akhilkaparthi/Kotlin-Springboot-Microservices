package com.landy.service.interview.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Address(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  val street1: String,
  val street2: String?,
  val postCode: String?,
  @ManyToOne val city: City,
)

data class AddressDto(
  val id: Long? = null,

  val street1: String,
  val street2: String?,
  val postCode: String?,
  val cityId: Long,
)

fun AddressDto.toEntity(city: City) = Address(
  id = this.id,
  street1 = this.street1,
  street2 = this.street2,
  postCode = this.postCode,
  city = city
)
