package com.example.catalogueservicepart.security

import com.example.catalogueservicepart.dto.UserDetailsDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationTokenFilter():OncePerRequestFilter() {

    @Autowired
    val jwtUtils:JwtUtils?=null

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt: String = request.getHeader("Authorization")

            val token = jwt.substring(7, jwt.length)

            if (token.isNotEmpty() && jwtUtils?.validateJwtToken(token) == true) {
                val userDetails: UserDetailsDTO = jwtUtils!!.getDetailsFromJwtToken(token)

                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails.username,
                    null,
                    userDetails.authorities
                )

                authentication.details = WebAuthenticationDetailsSource()
                    .buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            logger.error("Cannot set user authentication: {}", e)
        }

        filterChain.doFilter(request, response)
    }
}