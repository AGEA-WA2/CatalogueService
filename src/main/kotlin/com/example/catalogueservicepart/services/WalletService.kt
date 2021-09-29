package com.example.catalogueservicepart.services

import org.springframework.http.ResponseEntity

interface WalletService {
    fun getWallets(): ResponseEntity<*>
}
