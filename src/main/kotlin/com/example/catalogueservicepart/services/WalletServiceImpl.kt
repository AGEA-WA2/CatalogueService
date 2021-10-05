package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.CreateWalletDTO
import com.example.catalogueservicepart.dto.TransactionRequestDTO
import com.example.catalogueservicepart.repositories.UserRepository
import com.example.catalogueservicepart.utils.Utils
import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
@Transactional
class WalletServiceImpl(val userRepository: UserRepository, val restTemplate: RestTemplate,val utils: Utils): WalletService {

    @Qualifier("eurekaClient")
    @Autowired
    lateinit var eurekaClient: EurekaClient

    override fun getWallets(): ResponseEntity<*> {
        val username = SecurityContextHolder.getContext().authentication.principal.toString()
        val id = userRepository.findByUsername(username)?.getId()
        val addr = eurekaClient.getApplication("walletService").instances[0]
        return restTemplate.getForEntity("http://${addr.ipAddr}:${addr.port}/wallets?owner=$id", Set::class.java)
    }

    override fun createWallet(createWalletDTO: CreateWalletDTO):ResponseEntity<*> {
        val addr = eurekaClient.getApplication("walletService").instances[0]
        return restTemplate.postForEntity("http://${addr.ipAddr}:${addr.port}/wallets",createWalletDTO,CreateWalletDTO::class.java)
    }

    override fun addNegativeTransaction(walletId: Long, transactionRequestDTO: TransactionRequestDTO):ResponseEntity<*> {
        val addr = eurekaClient.getApplication("walletService").instances[0]
        return ResponseEntity(restTemplate.postForEntity("http://${addr.ipAddr}:${addr.port}/wallets/${walletId}/transactions",transactionRequestDTO,TransactionRequestDTO::class.java),HttpStatus.OK)
    }

    override fun addPositiveTransaction(walletId: Long, transactionRequestDTO: TransactionRequestDTO):ResponseEntity<*> {
        val addr = eurekaClient.getApplication("walletService").instances[0]
        return restTemplate.postForEntity("http://${addr.ipAddr}:${addr.port}/wallets/${walletId}/transactions",transactionRequestDTO,TransactionRequestDTO::class.java)
    }
}
