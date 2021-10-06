package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.CreateWalletDTO
import com.example.catalogueservicepart.dto.ResponseMessage
import com.example.catalogueservicepart.dto.TransactionRequestDTO
import com.example.catalogueservicepart.roles.Rolename
import com.example.catalogueservicepart.services.WalletService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("wallets")
class WalletController(val walletService: WalletService) {
    @GetMapping
    fun getWallet():ResponseEntity<*>{
        return ResponseEntity(walletService.getWallets(),HttpStatus.OK)
    }

    @PostMapping("/{walletId}/transaction")
    fun addTransactionToWallet(@PathVariable("walletId")walletId: Long,
                               @RequestBody transactionRequestDTO: TransactionRequestDTO):ResponseEntity<*>{
        return if (transactionRequestDTO.amount>0){
            val username = SecurityContextHolder.getContext().authentication.authorities.filter { it.authority=="ADMIN" }
            if(username.isNotEmpty()){
                ResponseEntity(walletService.addPositiveTransaction(walletId,transactionRequestDTO),HttpStatus.OK)
            }else{
                throw AccessDeniedException("Unauthorized user")
            }
        }else{
            ResponseEntity(walletService.addNegativeTransaction(walletId,transactionRequestDTO),HttpStatus.OK)
        }
    }

    @GetMapping("/{walletId}/transaction")
    fun getListOfWallet(@PathVariable("walletId")walletId: Long,
                        @RequestParam("from")from:Long,
                        @RequestParam("to")to:Long):ResponseEntity<*>{
        return ResponseEntity(walletService.getListTransactionBetween(walletId,from,to),HttpStatus.OK)
    }

    @GetMapping("/{walletId}/transaction/{transactionId}")
    fun getTransaction(@PathVariable("walletId")walletId: Long,
                       @PathVariable("transactionId")transactionId:Long):ResponseEntity<*>{
        return ResponseEntity(walletService.getSingleTransaction(walletId,transactionId),HttpStatus.OK)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    fun createWallet(@RequestBody createWalletDTO: CreateWalletDTO):ResponseEntity<*>{
        return ResponseEntity(walletService.createWallet(createWalletDTO),HttpStatus.OK)
    }
}
