package com.example.catalogueservicepart.dto

data class UpdateUserDTO(
    val username: String?,
    val address: String?,
    val role: Set<String>?,
    val isEnabled: Boolean?,
)
