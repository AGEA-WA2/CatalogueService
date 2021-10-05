package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.CreateWalletDTO
import com.example.catalogueservicepart.dto.TransactionRequestDTO
import com.example.catalogueservicepart.dto.WarehouseBodyDTO
import com.example.catalogueservicepart.dto.WarehousePatchDTO
import org.springframework.http.ResponseEntity

interface WarehouseService {

    fun getWarehouses(): ResponseEntity<*>
    fun getWarehouse(warehouseID: Long): ResponseEntity<*>
    fun addWarehouse(wh: WarehouseBodyDTO): ResponseEntity<*>
    fun updateWarehouse(warehouseID: Long, wh: WarehouseBodyDTO): ResponseEntity<*>
    fun patchWarehouse(warehouseID: Long, wh: WarehousePatchDTO): ResponseEntity<*>
    fun deleteWarehouse(warehouseID: Long): ResponseEntity<*>
}