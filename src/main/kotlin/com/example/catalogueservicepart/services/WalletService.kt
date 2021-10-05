package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.CreateWalletDTO
import com.example.catalogueservicepart.dto.TransactionDTO
import com.example.catalogueservicepart.dto.TransactionRequestDTO
import org.springframework.http.ResponseEntity

interface WalletService {
    fun getWallets(): ResponseEntity<*>
    fun createWallet(createWalletDTO: CreateWalletDTO):ResponseEntity<*>
    fun addPositiveTransaction(walletID:Long,transactionRequestDTO: TransactionRequestDTO):ResponseEntity<*>
    fun addNegativeTransaction(walletID:Long,transactionRequestDTO: TransactionRequestDTO):ResponseEntity<*>
    fun getListTransactionBetween(walletID:Long,from:Long,to:Long):ResponseEntity<*>
    fun getSingleTransaction(walletID:Long,transactionId:Long):ResponseEntity<*>
}
