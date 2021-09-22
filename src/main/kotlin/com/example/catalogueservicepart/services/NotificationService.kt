package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.domain.EmailVerificationToken
import com.example.catalogueservicepart.repositories.EmailVerificationTokenRepository
import com.example.catalogueservicepart.repositories.UserRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class NotificationService(
    val emailVerificationTokenRepository: EmailVerificationTokenRepository,
    val userRepository: UserRepository
) {

    fun createEmailVerificationToken(username: String): EmailVerificationToken {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Specified username doesn't exist")

        val emailVerificationToken = EmailVerificationToken()
        emailVerificationToken.user = user
        emailVerificationTokenRepository.save(emailVerificationToken)
        return emailVerificationToken
    }

    @Scheduled(fixedRate = 86400000)
    fun cleanExpiredToken() {
        emailVerificationTokenRepository.deleteByExpiryDateIsBefore(Date())
    }

}