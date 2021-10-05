package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.*
import com.example.catalogueservicepart.repositories.UserRepository
import com.example.catalogueservicepart.utils.Utils
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
@Transactional
class WarehouseServiceImpl(val userRepository: UserRepository, val restTemplate: RestTemplate, val utils: Utils): WarehouseService {
    override fun getWarehouse(warehouseID: Long): ResponseEntity<*> {
        TODO("Not yet implemented")
    }

    override fun getWarehouses(): ResponseEntity<*> {
        TODO("Not yet implemented")
    }

    override fun updateWarehouse(warehouseID: Long, wh: WarehouseBodyDTO): ResponseEntity<*> {
        val re = RequestEntity.put("${utils.buildUrl("WarehouseService")}/warehouses/${warehouseID}", warehouseID)
            .accept(MediaType.APPLICATION_JSON).body(wh)
        return restTemplate.exchange(re, WarehouseBodyDTO::class.java)
    }

    override fun deleteWarehouse(warehouseID: Long) {
         return restTemplate.delete("${utils.buildUrl("WarehouseService")}/warehouses/${warehouseID}",warehouseID)
    }

    override fun addWarehouse(wh: WarehouseBodyDTO): ResponseEntity<*> {
        TODO("Not yet implemented")
    }

    override fun patchWarehouse(warehouseID: Long, wh: WarehousePatchDTO): ResponseEntity<*> {
        val re = RequestEntity.patch("${utils.buildUrl("WarehouseService")}/warehouses/{warehouseID}", warehouseID)
            .accept(MediaType.APPLICATION_JSON).body(wh)
        return restTemplate.exchange(re, WarehousePatchDTO::class.java)
    }
}