package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.ResponseMessage
import com.example.catalogueservicepart.repositories.EmailVerificationTokenRepository
import com.example.catalogueservicepart.security.JwtUtils
import com.example.catalogueservicepart.services.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.constraints.Email


data class UserObject(
    val username: String,
    @Email
    var email: String,
    var password: String,
    var confirmPassword: String,
)

data class LoginRequest(
    val username: String,
    var password: String,
)

data class UserChangePassword(
    val username: String,
    val oldPassword:String,
    val newPassword:String,
)

@RestController
@RequestMapping("auth")
class AuthController(val emailVerificationTokenRepository: EmailVerificationTokenRepository,
                     val jwtUtils: JwtUtils,
                     val userDetailsService: UserDetailsServiceImpl) {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @PostMapping("/register")
    fun registerUser(@RequestBody userObject: UserObject): ResponseEntity<Any> {
        if (userObject.password != userObject.confirmPassword)
            return ResponseEntity.badRequest().body(ResponseMessage("Password and confirm password must be equal"))

        try {
            userDetailsService.createUser(userObject)
        }catch (e:Exception){
            return ResponseEntity.badRequest().body(ResponseMessage(e.message))
        }
        return ResponseEntity.ok(ResponseMessage("User registered"))
    }

    @GetMapping("/registrationConfirm")
    fun confirmUserRegistration(@RequestParam("token") token: String): ResponseEntity<Any> {
        val emailVerificationToken = emailVerificationTokenRepository.findByToken(token)
        if (Date().after(emailVerificationToken?.expiryDate) || emailVerificationToken == null)
            return ResponseEntity.badRequest().body(ResponseMessage("Token expired"))

        userDetailsService.confirmRegistration(emailVerificationToken.user.username)
        return ResponseEntity.ok(ResponseMessage("Registration confirmed"))
    }

    @PostMapping("/signin")
    fun signin(@RequestBody login: LoginRequest): ResponseEntity<Any> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(login.username, login.password)
        )

        return if (authentication.isAuthenticated) {
            val token = jwtUtils.generateJwtToken(authentication)
            ResponseEntity.ok(token)
        } else {
            ResponseEntity("Wrong username or password!", HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/changePassword")
    fun change(@RequestBody passwordParam:UserChangePassword,@RequestHeader("Authorization")token:String):ResponseEntity<Any>{
//        val authentication = authenticationManager.authenticate(
//            UsernamePasswordAuthenticationToken(passwordParam.username, passwordParam.oldPassword)
//        )
        try {
            //TODO controllare se l'utente da modificare è uguale all'utente attuale
            val checkToken=token.substring(7, token.length)
            if(jwtUtils.validateJwtToken(checkToken)){
                if(SecurityContextHolder.getContext().authentication.principal==passwordParam.username){
                    userDetailsService.changePassword(passwordParam.username,passwordParam.oldPassword,passwordParam.newPassword)
                }else{
                    userDetailsService.sendWarningEmail(passwordParam.username,"Someone has tried to change your password")
                    return ResponseEntity("Not Authorized to change other user password",HttpStatus.BAD_REQUEST,)
                }
            }else{
                userDetailsService.sendWarningEmail(passwordParam.username,"Someone has tried to change your password")
                return ResponseEntity("Not Authorized",HttpStatus.BAD_REQUEST,)
            }

        }catch (e:Exception){
            return ResponseEntity.badRequest().body(e.message)
        }
        return ResponseEntity.ok(ResponseMessage("Password modified successfully!"))

    }
}