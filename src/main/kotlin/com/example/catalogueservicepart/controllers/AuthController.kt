package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.ResponseMessage
import com.example.catalogueservicepart.dto.toUserDTO
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
    var firstname: String,
    var lastname: String,
    var address: String?,
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
            return ResponseEntity("Password and confirm password must be equal", HttpStatus.BAD_REQUEST)
        return ResponseEntity(userDetailsService.createUser(userObject).toUserDTO(),HttpStatus.OK)
    }

    @GetMapping("/registrationConfirm")
    fun confirmUserRegistration(@RequestParam("token") token: String): ResponseEntity<Any> {
        val emailVerificationToken = emailVerificationTokenRepository.findByToken(token)
        if (emailVerificationToken == null || Date().after(emailVerificationToken.expiryDate))
            return ResponseEntity("Token expired",HttpStatus.UNAUTHORIZED)

        return ResponseEntity(userDetailsService.confirmRegistration(emailVerificationToken.user.username),HttpStatus.OK)
    }

    @PostMapping("/signin")
    fun signin(@RequestBody login: LoginRequest): ResponseEntity<Any> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(login.username, login.password)
        )

        return if (authentication.isAuthenticated) {
            val token = jwtUtils.generateJwtToken(authentication)
            ResponseEntity(token,HttpStatus.OK)
        } else {
            ResponseEntity("Wrong username or password!", HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping("/changePassword")
    fun change(@RequestBody passwordParam:UserChangePassword,@RequestHeader("Authorization")token:String):ResponseEntity<Any>{
        val checkToken=token.substring(7, token.length)
        return if(jwtUtils.validateJwtToken(checkToken)){
            if(SecurityContextHolder.getContext().authentication.principal==passwordParam.username){
                ResponseEntity(userDetailsService.changePassword(passwordParam.username,passwordParam.oldPassword,passwordParam.newPassword),HttpStatus.OK)
            }else{
                userDetailsService.sendWarningEmail(passwordParam.username,"Someone has tried to change your password")
                ResponseEntity("Not Authorized",HttpStatus.UNAUTHORIZED,)
            }
        }else{
            userDetailsService.sendWarningEmail(passwordParam.username,"Someone has tried to change your password")
            ResponseEntity("Not Authorized",HttpStatus.UNAUTHORIZED,)
        }
    }
}
