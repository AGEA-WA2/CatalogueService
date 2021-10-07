package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.OrderDTO
import com.example.catalogueservicepart.dto.UpdateOrderDTO
import com.example.catalogueservicepart.repositories.UserRepository
import com.example.catalogueservicepart.roles.Status
import com.example.catalogueservicepart.utils.Utils
import com.netflix.discovery.DiscoveryClient
import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable


@Service
@Transactional
class OrderServiceImpl(val restTemplate: RestTemplate, val userRepository: UserRepository, val utils: Utils) :
    OrderService {

    override fun getOrders(): ResponseEntity<*> {
        val username = SecurityContextHolder.getContext().authentication.principal.toString()
        val id = userRepository.findByUsername(username)?.getId() ?: throw AccessDeniedException("Unauthorized user")
        return restTemplate.getForEntity("${utils.buildUrl("orderService")}/orders?buyer=$id", Set::class.java)
    }

    override fun getOrderById(order_id: Long): ResponseEntity<*> {
        val auth = SecurityContextHolder.getContext().authentication
        val id = userRepository.findByUsername(auth.principal.toString())?.getId()
        val response = restTemplate.getForEntity("${utils.buildUrl("orderService")}/orders/{orderId}", OrderDTO::class.java,order_id)
        if(response.statusCode==HttpStatus.BAD_REQUEST){
            return ResponseEntity("The order ID must be greater than or equal to 0.",HttpStatus.BAD_REQUEST)
        }else if (response.statusCode==HttpStatus.NOT_FOUND){
            return ResponseEntity("Order ID not found",HttpStatus.BAD_REQUEST)
        }else if (auth.authorities.none { it.authority == "ADMIN" } && response.body!!.buyer != id)
            throw AccessDeniedException("Unauthorized user")
        return response
    }

    override fun addNewOrder(orderDTO: OrderDTO): ResponseEntity<*> {
        return restTemplate.postForEntity("${utils.buildUrl("orderService")}/orders", orderDTO, OrderDTO::class.java)
    }

    override fun updateOrder(orderId: Long, updateOrderDTO: UpdateOrderDTO): ResponseEntity<*> {
        return ResponseEntity(
            restTemplate.patchForObject(
                "${utils.buildUrl("orderService")}/orders/$orderId",
                updateOrderDTO,
                OrderDTO::class.java
            ), HttpStatus.OK
        )
    }

    override fun deleteOrder(orderId: Long): ResponseEntity<*> {
        val updateOrderDTO = UpdateOrderDTO()
        updateOrderDTO.status = Status.CANCELED
        return ResponseEntity(
            restTemplate.patchForObject(
                "${utils.buildUrl("orderService")}/orders/$orderId",
                updateOrderDTO,
                OrderDTO::class.java
            ), HttpStatus.OK
        )
    }
}
