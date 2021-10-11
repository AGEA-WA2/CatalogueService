package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.PatchAlarmDTO
import com.example.catalogueservicepart.dto.ProductQuantityDTO
import com.example.catalogueservicepart.services.ProductQuantityService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/quantity") @Validated
class ProductQuantityController(val productQuantityService: ProductQuantityService) {
    @GetMapping
    fun getProductsQuantities(): ResponseEntity<*> {
        return productQuantityService.getProductQuantities()
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    fun createNewProductQuantity(@RequestBody pq: ProductQuantityDTO): ResponseEntity<*> {
        return productQuantityService.createOrUpdateProductQuantity(pq)
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{pqId}")
    fun patchAlarm(@PathVariable("pqId") pqId: Long, @RequestBody @NotNull @Valid alarm: PatchAlarmDTO): ResponseEntity<*> {
        return productQuantityService.patchAlarm(pqId, alarm)
    }
}