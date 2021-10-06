package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.ResponseMessage
import com.example.catalogueservicepart.services.UserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("admin")
class AdminController(val userDetailsService: UserDetailsServiceImpl) {


    @PostMapping("/makeAdmin")
    fun makeAdmin(@RequestParam("username") username: String): ResponseEntity<*> {
//        try {
//            userDetailsService.customerToAdmin(username)
//        } catch (e: Exception) {
//            return ResponseEntity.badRequest().body(ResponseMessage(e.message))
//        }
//        return ResponseEntity.ok(ResponseMessage("Changed successfully"))

        return ResponseEntity(userDetailsService.customerToAdmin(username),HttpStatus.OK)
    }

    @PostMapping("/makeCustomer")
    fun makeCustomer(@RequestParam("username") username: String): ResponseEntity<Any> {
//        try {
//            userDetailsService.adminToCustomer(username)
//        } catch (e: Exception) {
//            return ResponseEntity.badRequest().body(ResponseMessage(e.message))
//        }
//        return ResponseEntity.ok(ResponseMessage("Changed successfully"))
        return ResponseEntity(userDetailsService.adminToCustomer(username),HttpStatus.OK)
    }

}
