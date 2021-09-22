package com.example.catalogueservicepart.controllers;

import com.example.catalogueservicepart.dto.WarehouseBodyDTO
import com.example.catalogueservicepart.dto.WarehousePatchDTO
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("warehouses")
class WarehouseController {

    @GetMapping
    fun getAllWarehouses(){

    }

    @GetMapping("/{warehouseId}")
    fun getWarehouse(@PathVariable("warehouseId")warehouseId:Long){

    }

    @PostMapping
    fun addNewWarehouse(){}

    @PreAuthorize("ADMIN")
    @PutMapping("/{warehouseId}")
    fun updateWarehouse(@PathVariable("warehouseId")warehouseId: Long,@RequestBody warehouseDTO:WarehouseBodyDTO){

    }

    @PreAuthorize("ADMIN")
    @PatchMapping("/{warehouseId}")
    fun updatePartialWarehouse(@PathVariable("warehouseId")warehouseId: Long,@RequestBody warehousePatchDTO: WarehousePatchDTO){

    }

    @PreAuthorize("ADMIN")
    @DeleteMapping("/{warehouseId}")
    fun deleteWarehouse(@PathVariable("warehouseId")warehouseId: Long){
        
    }
}
