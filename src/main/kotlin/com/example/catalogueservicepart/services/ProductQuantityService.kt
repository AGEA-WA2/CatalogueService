package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.PatchAlarmDTO
import com.example.catalogueservicepart.dto.ProductQuantityDTO
import org.springframework.http.ResponseEntity

interface ProductQuantityService {
    fun getProductQuantities(): ResponseEntity<Collection<*>>
    fun createOrUpdateProductQuantity(pq: ProductQuantityDTO): ResponseEntity<ProductQuantityDTO>
    fun patchAlarm(id: Long, alarm: PatchAlarmDTO): ResponseEntity<ProductQuantityDTO>
}