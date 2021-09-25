package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.repositories.UserRepository
import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class WalletService(val userRepository: UserRepository, val restTemplate: RestTemplate) {

    @Qualifier("eurekaClient")
    @Autowired
    lateinit var eurekaClient: EurekaClient

    fun getWallets(): ResponseEntity<*> {
        val username = SecurityContextHolder.getContext().authentication.principal.toString()
        val id = userRepository.findByUsername(username)?.getId()
        val addr = eurekaClient.getApplication("walletService").instances[0]
        return restTemplate.getForEntity("http://${addr.ipAddr}:${addr.port}/wallets?owner=$id", Set::class.java)
    }
}
