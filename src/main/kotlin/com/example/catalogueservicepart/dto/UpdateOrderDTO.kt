package com.example.catalogueservicepart.dto

import com.example.catalogueservicepart.roles.Status
import javax.validation.Valid

data class UpdateOrderDTO(
    var id: Long? = null,
    var buyer: Long? = null,
    @field:Valid var products: MutableSet<ProductDTO>? = null,
    var status: Status? = null
)