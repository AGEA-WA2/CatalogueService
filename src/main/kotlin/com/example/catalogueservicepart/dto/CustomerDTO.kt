package com.example.catalogueservicepart.dto

import com.example.catalogueservicepart.domain.Customer
import javax.validation.constraints.NotNull

data class CustomerDTO(
    val id: Long?,
    @field:NotNull val firstName: String,
    @field:NotNull val lastName: String,
    @field:NotNull val delivery_address: String?
) {}

fun Customer.toCustomerDTO(): CustomerDTO {
    return CustomerDTO(
        this.getId(),
        firstName,
        lastName,
        deliveryAddress
    )
}

fun CustomerDTO.toCustomer(): Customer {
    val c = Customer()
    c.firstName = firstName
    c.lastName = lastName
    c.deliveryAddress = delivery_address
    return c
}
