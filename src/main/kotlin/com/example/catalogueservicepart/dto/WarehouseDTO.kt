package com.example.catalogueservicepart.dto

import javax.validation.constraints.NotNull

data class WarehouseDTO(
    val id: Long?,
    val whName: String?,
    val address: String
)

data class WarehouseBodyDTO(
    val whName: String?,
    @NotNull val address: String
)

data class WarehousePatchDTO(
    val whName: String?,
    val address: String?
)