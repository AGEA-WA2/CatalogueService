package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.ProductBodyDTO
import com.example.catalogueservicepart.dto.ProductDTO
import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
@Transactional
class ProductService(val restTemplate: RestTemplate) {

    @Qualifier("eurekaClient")
    @Autowired
    lateinit var eurekaClient: EurekaClient

    fun createProduct(product: ProductBodyDTO): ResponseEntity<*> {
        val addr = eurekaClient.getApplication("warehouseService").instances[0]
        return restTemplate.postForEntity("http://${addr.ipAddr}:${addr.port}/products", product, Any::class.java)
    }
}
