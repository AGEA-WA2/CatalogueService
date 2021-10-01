package com.example.catalogueservicepart.dto

import com.example.catalogueservicepart.roles.Status
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class OrderDTO(
    val id: Long? = null,
    @field:NotNull var buyer: Long? = null,
    @field:Valid var products: MutableSet<OrderProductDTO> = mutableSetOf(),
    var status: Status? = null
)

data class OrderBodyDTO(
    @field:NotNull var buyer:Long?=null,
    @field:Valid var products: MutableSet<ProductDTO> = mutableSetOf()
)
