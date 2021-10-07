package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.WarehouseBodyDTO
import com.example.catalogueservicepart.dto.WarehousePatchDTO
import com.example.catalogueservicepart.services.WarehouseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("warehouses")
class WarehouseController(val warehouseService: WarehouseService) {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    fun getAllWarehouses(): ResponseEntity<*> {
        return ResponseEntity(warehouseService.getWarehouses(),HttpStatus.OK)
    }

    @GetMapping("/{warehouseId}")
    fun getWarehouse(@PathVariable("warehouseId") warehouseId: Long): ResponseEntity<*> {
        return ResponseEntity(warehouseService.getWarehouse(warehouseId),HttpStatus.OK)
    }

    @PostMapping
    fun addNewWarehouse(@RequestBody @Valid wh: WarehouseBodyDTO): ResponseEntity<*> {
        return ResponseEntity(warehouseService.addWarehouse(wh),HttpStatus.OK)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{warehouseId}")
    fun updateWarehouse(
        @PathVariable("warehouseId") warehouseId: Long,
        @RequestBody warehouseDTO: WarehouseBodyDTO
    ): ResponseEntity<*> {
        return ResponseEntity(warehouseService.updateWarehouse(warehouseId,warehouseDTO),HttpStatus.OK)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{warehouseId}")
    fun updatePartialWarehouse(
        @PathVariable("warehouseId") warehouseId: Long,
        @RequestBody warehousePatchDTO: WarehousePatchDTO
    ): ResponseEntity<*> {
        return ResponseEntity(warehouseService.patchWarehouse(warehouseId,warehousePatchDTO),HttpStatus.OK)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{warehouseId}")
    fun deleteWarehouse(@PathVariable("warehouseId") warehouseId: Long): ResponseEntity<*> {
    return ResponseEntity(warehouseService.deleteWarehouse(warehouseId), HttpStatus.NO_CONTENT)
    }
}
