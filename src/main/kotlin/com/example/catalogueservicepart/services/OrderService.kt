package com.example.catalogueservicepart.services

import org.springframework.http.ResponseEntity

interface OrderService {
    fun getOrders(): ResponseEntity<*>
}
