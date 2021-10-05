package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.*
import com.example.catalogueservicepart.repositories.UserRepository
import com.example.catalogueservicepart.utils.Utils
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
@Transactional
class WarehouseServiceImpl(val userRepository: UserRepository, val restTemplate: RestTemplate, val utils: Utils) :
    WarehouseService {

    override fun getWarehouses(): ResponseEntity<*> {
        val url = "${utils.buildUrl("warehouseService")}/warehouses"
        return restTemplate.getForEntity(url, Array<WarehouseDTO>::class.java)
    }

    override fun getWarehouse(warehouseID: Long): ResponseEntity<*> {
        val url = "${utils.buildUrl("warehouseService")}/warehouses/{warehouseID}"
        return restTemplate.getForEntity(url, WarehouseDTO::class.java, warehouseID)
    }

    override fun addWarehouse(wh: WarehouseBodyDTO): ResponseEntity<*> {
        return restTemplate.postForEntity(
            "${utils.buildUrl("warehouseService")}/warehouses",
            wh,
            WarehouseDTO::class.java
        )
    }

    override fun updateWarehouse(warehouseID: Long, wh: WarehouseBodyDTO): ResponseEntity<*> {
        TODO("Not yet implemented")
    }

    override fun patchWarehouse(warehouseID: Long, wh: WarehousePatchDTO): ResponseEntity<*> {
        TODO("Not yet implemented")
    }

    override fun deleteWarehouse(warehouseID: Long): ResponseEntity<*> {
        TODO("Not yet implemented")
    }

}