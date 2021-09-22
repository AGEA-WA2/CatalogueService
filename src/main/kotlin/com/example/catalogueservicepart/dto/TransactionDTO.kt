package com.example.catalogueservicepart.dto

import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class TransactionDTO(
    val id: Long?,
    @field:NotNull val amount: Double,
    @field:NotNull val timestamp: Date,
    val reference: Long?,
    val walletID: Long?
)

data class TransactionRequestDTO(
    val amount: Double,
    @field:Positive val reference: Long
)