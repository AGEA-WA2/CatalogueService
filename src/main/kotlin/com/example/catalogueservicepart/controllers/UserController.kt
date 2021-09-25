package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.ResponseMessage
import com.example.catalogueservicepart.services.UserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class UserController(val userDetailsServiceImpl: UserDetailsServiceImpl) {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    fun getUsers(): ResponseEntity<Any> {
        return ResponseEntity(userDetailsServiceImpl.getUsers(), HttpStatus.OK)
    }

    @GetMapping("/info")
    fun getUser(): ResponseEntity<Any> {
        val res = userDetailsServiceImpl.getUser()
        return if (res == null) ResponseEntity(ResponseMessage("User not found"), HttpStatus.NOT_FOUND)
        else ResponseEntity(res, HttpStatus.OK)
    }
}
