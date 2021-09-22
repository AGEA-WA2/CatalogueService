package com.example.catalogueservicepart.repositories

import com.example.catalogueservicepart.domain.User
import org.springframework.data.repository.CrudRepository

interface UserRepository:CrudRepository<User,Long> {
    fun findByUsername(username: String): User?
    fun findByEmail(email:String):User?
}