package com.example.catalogueservicepart.dto

import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class OrderProductDTO(
    var id: Long? = null,
    @field:NotNull var productName: String? = null,
    var description: String? = null,
    var pictureUrl: String? = null,
    var category: String? = null,
    @field:Min(0) var price: Double? = null,
    @field:Min(0) var amount: Int? = null
)


data class ProductDTO(
    val id: Long?,
    val productName: String,
    val description: String?,
    val category: String?,
    val pictureUrl: String?,
    val price: Double,
    val avgRating: Double,
    val creationDate: Date?
)

data class ProductBodyDTO(
    @NotNull val productName: String,
    val description: String?,
    val category: String?,
    val pictureUrl: String?,
    @NotNull @Positive val price: Double
)

data class ProductPatchDTO(
    val productName: String?,
    val description: String?,
    val pictureUrl: String?,
    val category: String?,
    val price: Double?,
    val avgRating: Double?,
    val creationDate: Date?
)