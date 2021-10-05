package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.CreateWalletDTO
import com.example.catalogueservicepart.dto.TransactionDTO
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

    override fun addNegativeTransaction(walletID: Long, transactionRequestDTO: TransactionRequestDTO):ResponseEntity<*> {
        val addr = eurekaClient.getApplication("walletService").instances[0]
        return restTemplate.postForEntity("http://${addr.ipAddr}:${addr.port}/wallets/${walletID}/transactions",transactionRequestDTO,TransactionRequestDTO::class.java)
    }

    override fun addPositiveTransaction(walletID: Long, transactionRequestDTO: TransactionRequestDTO):ResponseEntity<*> {
        val addr = eurekaClient.getApplication("walletService").instances[0]
        return restTemplate.postForEntity("http://${addr.ipAddr}:${addr.port}/wallets/${walletID}/transactions",transactionRequestDTO,TransactionRequestDTO::class.java)
    }

    override fun getListTransactionBetween(walletID: Long, from: Long, to: Long): ResponseEntity<*> {
        val url = "${utils.buildUrl("walletService")}/wallets/${walletID}/transactions?from={from}&to={to}"
        return restTemplate.getForEntity(url,Array<TransactionDTO>::class.java,from,to)
    }

    override fun getSingleTransaction(walletID: Long, transactionId: Long):ResponseEntity<*> {
        val url = "${utils.buildUrl("walletService")}/wallets/${walletID}/transactions/${transactionId}"
        //TODO Togliere ID dalla risposta
        return restTemplate.getForEntity(url,TransactionDTO::class.java)
    }

}
