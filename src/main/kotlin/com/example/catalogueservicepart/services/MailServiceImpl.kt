package com.example.catalogueservicepart.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class MailServiceImpl(val mailSender: JavaMailSender): MailService {

    @Value("\${spring.mail.username}")
    lateinit var fromMail: String

    override fun sendMessage(toMail: String, subject: String, mailBody: String) {
        val simpleMailMessage = SimpleMailMessage()
        simpleMailMessage.setFrom(fromMail)
        simpleMailMessage.setTo(toMail)
        simpleMailMessage.setSubject(subject)
        simpleMailMessage.setText(mailBody)

        mailSender.send(simpleMailMessage)
    }
}
