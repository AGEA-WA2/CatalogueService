package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.domain.EmailVerificationToken

interface NotificationService {
    fun createEmailVerificationToken(username: String): EmailVerificationToken
}
