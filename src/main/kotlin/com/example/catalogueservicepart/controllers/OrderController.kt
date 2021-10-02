package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.OrderBodyDTO
import com.example.catalogueservicepart.dto.OrderDTO
import com.example.catalogueservicepart.dto.ResponseMessage
import com.example.catalogueservicepart.dto.UpdateOrderDTO
import com.example.catalogueservicepart.roles.Status
import com.example.catalogueservicepart.services.OrderService
import com.example.catalogueservicepart.utils.Utils
import org.springframework.http.HttpStatus
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
        return restTemplate.postForEntity("${utils.buildUrl("orderService")}/orders", orderDTO, OrderDTO::class.java)
    }

    @PatchMapping("/{orderId}")
    fun updateOrder(
        @PathVariable("orderId") orderId: Long,
        @RequestBody updateOrderDTO: UpdateOrderDTO
    ): ResponseEntity<*> {
        val obj = restTemplate.patchForObject(
            "${utils.buildUrl("orderService")}/orders/$orderId",
            updateOrderDTO,
            OrderDTO::class.java
        )
        return ResponseEntity(obj, HttpStatus.OK)
    }

    @DeleteMapping("/{orderId}")
    fun deleteOrder(@PathVariable("orderId") orderId: Long): ResponseEntity<*> {
        val updateOrderDTO = UpdateOrderDTO()
        updateOrderDTO.status = Status.CANCELED
        val obj = restTemplate.patchForObject(
            "${utils.buildUrl("orderService")}/orders/$orderId",
            updateOrderDTO,
            OrderDTO::class.java
        )
        return ResponseEntity(obj, HttpStatus.OK)
    }
}
