package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.CreateWalletDTO
import com.example.catalogueservicepart.dto.ResponseMessage
import com.example.catalogueservicepart.dto.TransactionRequestDTO
import com.example.catalogueservicepart.roles.Rolename
import com.example.catalogueservicepart.services.WalletService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("wallets")
class WalletController(val walletService: WalletService) {
    @GetMapping
    fun getWallet():ResponseEntity<*>{
        return walletService.getWallets()
    }

    @PostMapping("/{walletId}/transaction")
    fun addTransactionToWallet(@PathVariable("walletId")walletId: Long,
                               @RequestBody transactionRequestDTO: TransactionRequestDTO):ResponseEntity<*>{
        if (transactionRequestDTO.amount>0){
            val username = SecurityContextHolder.getContext().authentication.authorities
            if(username.contains(GrantedAuthority { "ADMIN" })){
                return walletService.addPositiveTransaction(walletId,transactionRequestDTO)
            }else{
                return ResponseEntity.ok(ResponseMessage("Not Authorized"))
            }
        }else{
            return walletService.addNegativeTransaction(walletId,transactionRequestDTO)
        }
    }

    @GetMapping("/{walletId}/transaction")
    fun getListOfWallet(@PathVariable("walletId")walletId: Long,
                        @RequestParam("from")from:Long,
                        @RequestParam("to")to:Long){
        println("------------->>>>>QUI")

    }

    @GetMapping("/{walletId}/transaction/{transactionId}")
    fun getTransaction(@PathVariable("walletId")walletId: Long,@PathVariable("transactionId")transactionId:Long){
        println("------------->>>>>QUAAAAA")
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    fun createWallet(@RequestBody createWalletDTO: CreateWalletDTO):ResponseEntity<*>{
        return walletService.createWallet(createWalletDTO)
    }
}
