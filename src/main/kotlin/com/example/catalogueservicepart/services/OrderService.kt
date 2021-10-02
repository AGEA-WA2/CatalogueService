package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.OrderDTO
import com.example.catalogueservicepart.dto.UpdateOrderDTO
import org.springframework.http.ResponseEntity

interface OrderService {
    fun getOrders(): ResponseEntity<*>
    fun getOrderById(order_id: Long): ResponseEntity<*>
    fun addNewOrder(orderDTO: OrderDTO): ResponseEntity<*>
    fun updateOrder(orderId: Long, updateOrderDTO: UpdateOrderDTO): ResponseEntity<*>
    fun deleteOrder(orderId: Long): ResponseEntity<*>
}
