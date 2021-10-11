package com.example.catalogueservicepart.dto

import com.example.catalogueservicepart.domain.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.validation.Valid
import javax.validation.constraints.NotNull

class UserDetailsDTO(
    val id: Long?,
    @field:NotNull private val username: String,
    @JsonIgnore
    @field:NotNull private val password: String,
    @field:NotNull val email: String,
    @field:NotNull private val address:String,
    @field:NotNull val firstname: String,
    @field:NotNull val lastname: String,
    private val role:String,
    private val isEnabled: Boolean,
    @field:Valid private val authorities: MutableList<GrantedAuthority>?
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    fun getRole():String{
        return role
    }

    fun getAddress():String{
        return address
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return isEnabled
    }
}

fun User.toUserDetailsDTO(): UserDetailsDTO {
    return UserDetailsDTO(
        this.getId(),
        username,
        password,
        email,
        address?: "",
        firstName,
        lastName,
        roles,
        isEnabled,
        retrieveRoles().map { SimpleGrantedAuthority(it.name) }.toMutableList()
    )
}

fun UserDetailsDTO.toUserDTO(): UserDTO {
    return UserDTO(
        id,
        username,
        email,
        firstname,
        lastname,
        getAddress(),
        getRole(),
        isEnabled
    )
}
