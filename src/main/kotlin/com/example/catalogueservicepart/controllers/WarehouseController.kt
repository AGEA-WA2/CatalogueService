package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.WarehouseBodyDTO
import com.example.catalogueservicepart.dto.WarehousePatchDTO
import com.example.catalogueservicepart.services.WarehouseService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("warehouses")
class WarehouseController(val warehouseService: WarehouseService) {

    @GetMapping
    fun getAllWarehouses(): ResponseEntity<*> {
        return warehouseService.getWarehouses()
    }

    @GetMapping("/{warehouseId}")
    fun getWarehouse(@PathVariable("warehouseId") warehouseId: Long): ResponseEntity<*> {
        return warehouseService.getWarehouse(warehouseId)
    }

    @PostMapping
    fun addNewWarehouse(@RequestBody @Valid wh: WarehouseBodyDTO): ResponseEntity<*> {
        return warehouseService.addWarehouse(wh)
    }

    @PreAuthorize("ADMIN")
    @PutMapping("/{warehouseId}")
    fun updateWarehouse(
        @PathVariable("warehouseId") warehouseId: Long,
        @RequestBody warehouseDTO: WarehouseBodyDTO
    ): ResponseEntity<*> {

    }

    @PreAuthorize("ADMIN")
    @PatchMapping("/{warehouseId}")
    fun updatePartialWarehouse(
        @PathVariable("warehouseId") warehouseId: Long,
        @RequestBody warehousePatchDTO: WarehousePatchDTO
    ): ResponseEntity<*> {

    }

    @PreAuthorize("ADMIN")
    @DeleteMapping("/{warehouseId}")
    fun deleteWarehouse(@PathVariable("warehouseId") warehouseId: Long): ResponseEntity<*> {

    }
}
