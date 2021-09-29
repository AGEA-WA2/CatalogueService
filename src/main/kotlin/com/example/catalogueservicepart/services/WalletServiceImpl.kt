package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.repositories.UserRepository
import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
@Transactional
class WalletServiceImpl(val userRepository: UserRepository, val restTemplate: RestTemplate): WalletService {

    @Qualifier("eurekaClient")
    @Autowired
    lateinit var eurekaClient: EurekaClient

    override fun getWallets(): ResponseEntity<*> {
        val username = SecurityContextHolder.getContext().authentication.principal.toString()
        val id = userRepository.findByUsername(username)?.getId()
        val addr = eurekaClient.getApplication("walletService").instances[0]
        return restTemplate.getForEntity("http://${addr.ipAddr}:${addr.port}/wallets?owner=$id", Set::class.java)
    }
}
