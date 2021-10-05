package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.CreateWalletDTO
import com.example.catalogueservicepart.dto.TransactionRequestDTO
import com.example.catalogueservicepart.repositories.UserRepository
import com.example.catalogueservicepart.utils.Utils
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
@Transactional
class WarehouseServiceImpl(val userRepository: UserRepository, val restTemplate: RestTemplate, val utils: Utils): WalletService {
    override fun getWallets(): ResponseEntity<*> {
        TODO("Not yet implemented")
    }

    override fun createWallet(createWalletDTO: CreateWalletDTO): ResponseEntity<*> {
        TODO("Not yet implemented")
    }

    override fun addPositiveTransaction(
        walletID: Long,
        transactionRequestDTO: TransactionRequestDTO
    ): ResponseEntity<*> {
        TODO("Not yet implemented")
    }

    override fun addNegativeTransaction(
        walletID: Long,
        transactionRequestDTO: TransactionRequestDTO
    ): ResponseEntity<*> {
        TODO("Not yet implemented")
    }

    override fun getListTransactionBetween(walletID: Long, from: Long, to: Long): ResponseEntity<*> {
        TODO("Not yet implemented")
    }

    override fun getSingleTransaction(walletID: Long, transactionId: Long): ResponseEntity<*> {
        TODO("Not yet implemented")
    }
}