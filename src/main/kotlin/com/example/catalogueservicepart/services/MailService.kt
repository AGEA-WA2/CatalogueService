package com.example.catalogueservicepart.services

interface MailService {
    fun sendMessage(toMail: String, subject: String, mailBody: String)
}
