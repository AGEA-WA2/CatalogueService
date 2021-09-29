package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.OrderDTO
import com.example.catalogueservicepart.repositories.UserRepository
import com.netflix.discovery.DiscoveryClient
import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class OrderServiceImpl(val restTemplate: RestTemplate, val userRepository: UserRepository): OrderService {

    @Qualifier("eurekaClient")
    @Autowired
    lateinit var eurekaClient: EurekaClient

    override fun getOrders(): ResponseEntity<*> {
        val username = SecurityContextHolder.getContext().authentication.principal.toString()
        val id = userRepository.findByUsername(username)?.getId()
        val addr = eurekaClient.getApplication("orderService").instances[0]
        return restTemplate.getForEntity("http://${addr.ipAddr}:${addr.port}/orders?buyer=$id", Set::class.java)
    }
}
