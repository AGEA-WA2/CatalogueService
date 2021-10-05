package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.CreateWalletDTO
import com.example.catalogueservicepart.dto.TransactionDTO
import com.example.catalogueservicepart.dto.TransactionRequestDTO
import org.springframework.http.ResponseEntity

interface WalletService {
    fun getWallets(): ResponseEntity<*>
    fun createWallet(createWalletDTO: CreateWalletDTO):ResponseEntity<*>
    fun addPositiveTransaction(walletId:Long,transactionRequestDTO: TransactionRequestDTO):ResponseEntity<*>
    fun addNegativeTransaction(walletId:Long,transactionRequestDTO: TransactionRequestDTO):ResponseEntity<*>
}
