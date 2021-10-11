package com.example.catalogueservicepart.dto

import javax.validation.constraints.Positive

data class WalletDTO(
    val id: Long?,
    val balance: Double,
    val userID: Long?
)

data class CreateWalletDTO(
    @field:Positive val userID: Long,
    val balance: Double)