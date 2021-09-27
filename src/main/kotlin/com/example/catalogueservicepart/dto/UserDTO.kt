package com.example.catalogueservicepart.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import javax.validation.Valid
import javax.validation.constraints.NotNull

data class UserDTO (
    val id: Long?,
    val username: String,
    val email: String,
    val address:String,
    val role:String,
    val isEnabled: Boolean,
)
