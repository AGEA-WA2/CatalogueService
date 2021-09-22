package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.ResponseMessage
import com.example.catalogueservicepart.security.JwtUtils
import com.example.catalogueservicepart.services.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("admin")
class adminController (val jwtUtils: JwtUtils,
                       val userDetailsService: UserDetailsServiceImpl){

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @PostMapping("/makeAdmin")
    fun makeAdmin(@RequestParam("username")username:String ): ResponseEntity<Any> {
        try {
            userDetailsService.customerToAdmin(username)
        }catch (e:Exception){
            return ResponseEntity.badRequest().body(ResponseMessage(e.message))
        }
        return ResponseEntity.ok(ResponseMessage("Changed successfully"))
    }

    @PostMapping("/makeCustomer")
    fun makeCustomer(@RequestParam("username")username:String ): ResponseEntity<Any> {
        try {
            userDetailsService.adminToCustomer(username)
        }catch (e:Exception){
            return ResponseEntity.badRequest().body(ResponseMessage(e.message))
        }
        return ResponseEntity.ok(ResponseMessage("Changed successfully"))
    }

}