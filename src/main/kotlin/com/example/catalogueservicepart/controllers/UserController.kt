package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.ResponseMessage
import com.example.catalogueservicepart.dto.UpdateUserDTO
import com.example.catalogueservicepart.dto.UserDetailsDTO
import com.example.catalogueservicepart.dto.toUserDTO
import com.example.catalogueservicepart.services.UserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
class UserController(val userDetailsServiceImpl: UserDetailsServiceImpl) {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    fun getUsers(): ResponseEntity<Any> {
        return ResponseEntity(userDetailsServiceImpl.getUsers().map { it.toUserDTO() }, HttpStatus.OK)
    }

    @GetMapping("/{username}")
    fun getUser(@PathVariable username: String): ResponseEntity<Any> {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth.authorities.none { it.authority == "ADMIN" } && auth.principal != username)
            throw AccessDeniedException("Unauthorized user")

        val res = userDetailsServiceImpl.loadUserByUsername(username).toUserDTO()
        return ResponseEntity(res, HttpStatus.OK)
    }

    @PatchMapping("/{username}")
    fun updateUser(@PathVariable username: String, @RequestBody updateUserDTO: UpdateUserDTO): ResponseEntity<Any> {
        val auth = SecurityContextHolder.getContext().authentication
        lateinit var user: UserDetailsDTO
        if (auth.authorities.none { it.authority == "ADMIN" } && auth.principal != username)
            throw AccessDeniedException("Unauthorized user")

        if (updateUserDTO.username != null) user =
            userDetailsServiceImpl.changeUsername(username, updateUserDTO.username)
                ?: return ResponseEntity(ResponseMessage("User not found"), HttpStatus.NOT_FOUND)

        if (updateUserDTO.address != null) user = userDetailsServiceImpl.changeAddress(updateUserDTO.address, username)
            ?: return ResponseEntity(ResponseMessage("User not found"), HttpStatus.NOT_FOUND)

        if (updateUserDTO.role != null && auth.authorities.any { it.authority == "ADMIN" }) {
            user = userDetailsServiceImpl.removeRole("ADMIN", username)
            user = userDetailsServiceImpl.removeRole("CUSTOMER", username)
            updateUserDTO.role.forEach { user = userDetailsServiceImpl.addRole(it, username) }
        }

        if (updateUserDTO.isEnabled != null && auth.authorities.any { it.authority == "ADMIN" })
            user = if (updateUserDTO.isEnabled) userDetailsServiceImpl.enableUser(username)
            else userDetailsServiceImpl.disableUser(username)

        return ResponseEntity(user.toUserDTO(), HttpStatus.OK)
    }

    //TODO(aggiungere l'exception handler)
}
