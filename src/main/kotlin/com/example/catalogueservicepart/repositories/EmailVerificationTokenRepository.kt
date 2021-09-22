package com.example.catalogueservicepart.repositories

import com.example.catalogueservicepart.domain.EmailVerificationToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmailVerificationTokenRepository:CrudRepository<EmailVerificationToken,Long> {
    fun findByToken(token: String): EmailVerificationToken?
    fun deleteByExpiryDateIsBefore(date: Date)
}