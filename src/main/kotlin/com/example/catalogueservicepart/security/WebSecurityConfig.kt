package com.example.catalogueservicepart.security

import com.example.catalogueservicepart.services.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class WebSecurityConfig:WebSecurityConfigurerAdapter() {

    @Autowired
    val passwordEncoder: PasswordEncoder? = null
    @Autowired
    val userDetailsService: UserDetailsServiceImpl? = null
    @Autowired
    val authenticationEntryPoint: AuthEntryPoint? = null
    @Autowired
    val jwtAuthenticationTokenFilter: JwtAuthenticationTokenFilter? = null

    @Override
    override fun configure(httpSecurity: HttpSecurity){
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .cors()
            .and()
            .csrf()
            .disable()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/auth/**")
            .permitAll()
            .and().authorizeRequests().antMatchers("/products/**").permitAll().and()
            .authorizeRequests()
            .anyRequest().fullyAuthenticated()
    }

    @Override
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

}