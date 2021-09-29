package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.OrderBodyDTO
import com.example.catalogueservicepart.services.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("orders")
class OrderController(val orderService: OrderService) {

    @GetMapping
    fun allListOfOrder():ResponseEntity<*>{
        return orderService.getOrders()
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
