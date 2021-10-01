package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.OrderBodyDTO
import com.example.catalogueservicepart.dto.OrderDTO
import com.example.catalogueservicepart.dto.ResponseMessage
import com.example.catalogueservicepart.services.OrderService
import com.example.catalogueservicepart.utils.Utils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import javax.validation.Valid

@RestController
@RequestMapping("orders")
class OrderController(val orderService: OrderService, val restTemplate: RestTemplate, val utils: Utils) {

    @GetMapping
    fun allListOfOrder(): ResponseEntity<*> {
        return orderService.getOrders()
    }

    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable("orderId") orderId: Long) {

    }

    @PostMapping
    fun addNewOrder(@RequestBody @Valid orderDTO: OrderDTO, br: BindingResult): ResponseEntity<*> {
        println()
        return restTemplate.postForEntity("${utils.buildUrl("orderService")}/orders", orderDTO, OrderDTO::class.java)
    }

    @PatchMapping("/{orderId}")
    fun updateOrder(@PathVariable("orderId") orderId: Long, @RequestBody orderBodyDTO: OrderBodyDTO) {

    }

    @DeleteMapping("/{orderId}")
    fun deleteOrder(@PathVariable("orderId") orderId: Long) {

    }
}
