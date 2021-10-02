package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.OrderBodyDTO
import com.example.catalogueservicepart.dto.OrderDTO
import com.example.catalogueservicepart.dto.ResponseMessage
import com.example.catalogueservicepart.dto.UpdateOrderDTO
import com.example.catalogueservicepart.roles.Status
import com.example.catalogueservicepart.services.OrderService
import com.example.catalogueservicepart.utils.ApiError
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
    fun getOrder(@PathVariable("orderId") orderId: Long): ResponseEntity<*> {
        return orderService.getOrderById(orderId)
    }

    @PostMapping
    fun addNewOrder(@RequestBody @Valid orderDTO: OrderDTO, br: BindingResult): ResponseEntity<*> {
        return if (br.hasErrors()) ResponseEntity(
            ApiError(HttpStatus.BAD_REQUEST, "Bad Request", "${br.fieldError?.field} - ${br.fieldError?.defaultMessage}"),
            HttpStatus.BAD_REQUEST
        )
        else orderService.addNewOrder(orderDTO)
    }

    @PatchMapping("/{orderId}")
    fun updateOrder(
        @PathVariable("orderId") orderId: Long,
        @RequestBody updateOrderDTO: UpdateOrderDTO
    ): ResponseEntity<*> {
        return orderService.updateOrder(orderId, updateOrderDTO)
    }

    @DeleteMapping("/{orderId}")
    fun deleteOrder(@PathVariable("orderId") orderId: Long): ResponseEntity<*> {
        return deleteOrder(orderId)
    }
}
