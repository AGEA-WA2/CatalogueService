package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.CreateWalletDTO
import com.example.catalogueservicepart.dto.TransactionRequestDTO
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wallets")
class WalletController {
    @GetMapping("/{walletId}")
    fun getWallet(@PathVariable("walletId")walletId:Long):ResponseEntity<Any>{
        return ResponseEntity.ok("Sei autenticato con il wallet: $walletId")
    }

    @PreAuthorize("ADMIN")
    @PostMapping
    fun createWallet(@RequestBody createWalletDTO: CreateWalletDTO){

    }

    @PostMapping("/{walletId}/transaction")
    fun addTransactionToWallet(@PathVariable("walletId")walletId: Long,transactionRequestDTO: TransactionRequestDTO){
        if (transactionRequestDTO.amount>0){
            //TODO se maggiore di 0 deve essere un ADMIN
        }
    }

    @GetMapping("/{walletId}/transaction")
    fun getListOfWallet(@PathVariable("walletId")walletId: Long,
                        @RequestParam("from")from:Long,
                        @RequestParam("to")to:Long){

    }

    @GetMapping("/{walletId}/transaction/{transactionId}")
    fun getTransaction(@PathVariable("walletId")walletId: Long,@PathVariable("transactionId")transactionId:Long){

    }

}