package com.example.catalogueservicepart.security

import com.example.catalogueservicepart.dto.UserDetailsDTO
import com.example.catalogueservicepart.services.UserDetailsServiceImpl

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import io.jsonwebtoken.UnsupportedJwtException

import io.jsonwebtoken.ExpiredJwtException

import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException


@Component
class JwtUtils(val userDetailsService: UserDetailsServiceImpl) {

    @Value("\${application.jwt.jwtExpirationMs}")
    lateinit var expirationTime: String

    @Value("\${application.jwt.jwtSecret}")
    lateinit var jwtSecretKey: String

    @Value("\${application.jwt.jwtHeader}")
    lateinit var applicationHeaders: String

    @Value("\${application.jwt.jwtHeaderStart}")
    lateinit var bearerHeader: String

    fun generateJwtToken(authentication: Authentication): String {
        val date = Date()
        val d2 = date.time + expirationTime.toLong()
        val key = jwtSecretKey.encodeToByteArray()

        // rebuild key using SecretKeySpec
        val originalKey: SecretKey = SecretKeySpec(key, 0, key.size, "HMACSHA256")
        return Jwts.builder()
            .setIssuer(authentication.name)
            .claim("role", authentication.authorities)
            .setIssuedAt(date)
            .setExpiration(Date(d2))
            .signWith(
                originalKey
            ).compact()
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            val key = jwtSecretKey.encodeToByteArray()
            val originalKey: SecretKey = SecretKeySpec(key, 0, key.size, "HMACSHA256")
            Jwts.parserBuilder().setSigningKey(originalKey)
                .build()
                .parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            println("Invalid JWT signature: {${e.message}}")
        } catch (e: MalformedJwtException) {
            println("Invalid JWT token: {${e.message}}")
        } catch (e: ExpiredJwtException) {
            println("JWT token is expired: {${e.message}}")
        } catch (e: UnsupportedJwtException) {
            println("JWT token is unsupported: {${e.message}}")
        } catch (e: IllegalArgumentException) {
            println("JWT claims string is empty: {${e.message}}")
        }
        return false
    }

    //campo role in UserDetailsDTO
    fun getDetailsFromJwtToken(authToken: String): UserDetailsDTO {
        val key = jwtSecretKey.encodeToByteArray()
        val originalKey: SecretKey = SecretKeySpec(key, 0, key.size, "HMACSHA256")
        val claims = Jwts.parserBuilder().setSigningKey(originalKey)
            .build().parseClaimsJws(authToken)
        return userDetailsService.loadUserByUsername(claims.body.issuer)
    }

}