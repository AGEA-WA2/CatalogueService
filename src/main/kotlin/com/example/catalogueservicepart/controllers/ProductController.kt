package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.ProductBodyDTO
import com.example.catalogueservicepart.dto.ProductPatchDTO
import com.example.catalogueservicepart.security.JwtUtils
import com.example.catalogueservicepart.services.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("products")
class ProductController(val productService: ProductService) {

    lateinit var authenticationManager: AuthenticationManager

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    fun addNewProduct(@RequestBody newProduct: ProductBodyDTO): ResponseEntity<*> {
        return productService.createProduct(newProduct)
    }

    @GetMapping
    fun retreiveProductByCategory(@RequestParam("category") category: String) {

    }

    @GetMapping("/{productId}")
    fun getProductId(@PathVariable("productId") productId: Long) {

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{productId}")
    fun updateProductById(@PathVariable("productId") productId: Long, @RequestBody productBodyDTO: ProductBodyDTO) {

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{productId}")
    fun pathProductById(@PathVariable("productId") productId: Long, @RequestBody productPatchDTO: ProductPatchDTO) {

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable("productId") productId: Long) {

    }

    @GetMapping("/{productId}/picture")
    fun getPicture(@PathVariable("productId") productId: Long) {

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{productId}/picture")
    fun updatePicture(@PathVariable("productId") productId: Long, @RequestBody pictureUrl: String) {

    }

    @GetMapping("/{productId}/warehouse")
    fun getWarehouseByProduct(@PathVariable("productId") productId: Long) {

    }
}
