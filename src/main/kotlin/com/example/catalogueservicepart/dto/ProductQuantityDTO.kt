package com.example.catalogueservicepart.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class ProductQuantityDTO(
    val id: Long?,
    @NotNull val warehouseId: Long?,
    @NotNull val productId: Long?,
    val quantity: Int?,
    val alarm: Int?
)

data class OrderToWarehouseDTO(
    @NotNull val productId: Long,
    @NotNull @Min(1) val quantity: Int
)

data class PatchAlarmDTO(@field: Positive val alarm: Int)


