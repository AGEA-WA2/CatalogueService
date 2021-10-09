package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.PatchAlarmDTO
import com.example.catalogueservicepart.dto.ProductDTO
import com.example.catalogueservicepart.dto.ProductQuantityDTO
import com.example.catalogueservicepart.dto.WarehouseDTO
import com.example.catalogueservicepart.utils.Utils
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
@Transactional
class ProductQuantityServiceImpl(val restTemplate: RestTemplate, val utils: Utils):ProductQuantityService {
    override fun getProductQuantities(): ResponseEntity<Collection<*>> {
        return restTemplate.getForEntity(
            "${utils.buildUrl("warehouseService")}/quantity",Collection::class.java)
    }

    override fun createOrUpdateProductQuantity(pq: ProductQuantityDTO): ResponseEntity<ProductQuantityDTO> {
        val re = RequestEntity.put("${utils.buildUrl("warehouseService")}/quantity")
            .accept(MediaType.APPLICATION_JSON).body(pq)
        return restTemplate.exchange(re,
            ProductQuantityDTO::class.java)
    }

    override fun patchAlarm(id: Long, alarm: PatchAlarmDTO): ResponseEntity<ProductQuantityDTO> {
        val re = RequestEntity.patch("${utils.buildUrl("warehouseService")}/quantity/{pqId}", id)
            .accept(MediaType.APPLICATION_JSON).body(alarm)
        return restTemplate.exchange(re,ProductQuantityDTO::class.java)
    }
}