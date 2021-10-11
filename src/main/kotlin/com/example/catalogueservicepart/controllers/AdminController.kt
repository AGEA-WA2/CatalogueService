package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.ResponseMessage
import com.example.catalogueservicepart.services.UserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("admin")
class AdminController(val userDetailsService: UserDetailsServiceImpl) {


    @PostMapping("/makeAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun makeAdmin(@RequestParam("username") username: String): ResponseEntity<*> {
        return ResponseEntity(userDetailsService.customerToAdmin(username),HttpStatus.OK)
    }

    @PostMapping("/makeCustomer")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun makeCustomer(@RequestParam("username") username: String): ResponseEntity<Any> {
        return ResponseEntity(userDetailsService.adminToCustomer(username),HttpStatus.OK)
    }

}
