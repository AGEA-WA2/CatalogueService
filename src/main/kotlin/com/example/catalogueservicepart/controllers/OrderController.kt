package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.OrderBodyDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("orders")
class OrderController {

    @GetMapping
    fun allListOfOrder():ResponseEntity<Any>{
        return ResponseEntity.ok("Sei autenticato")
    }

    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable("orderId")orderId:Long){

    }

    @PostMapping
    fun addNewOrder(@RequestBody orderBodyDTO: OrderBodyDTO){

    }

    @PatchMapping("/{orderId}")
    fun updateOrder(@PathVariable("orderId")orderId: Long, @RequestBody orderBodyDTO: OrderBodyDTO){

    }

    @DeleteMapping("/{orderId}")
    fun deleteOrder(@PathVariable("orderId")orderId: Long){

    }
}