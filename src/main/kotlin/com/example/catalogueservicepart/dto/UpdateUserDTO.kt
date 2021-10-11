package com.example.catalogueservicepart.dto

import javax.validation.constraints.Email

data class UpdateUserDTO(
    val username: String?,
    val address: String?,
    val firstname: String?,
    val lastname: String?,
    val role: Set<String>?,
    val isEnabled: Boolean?,
)
