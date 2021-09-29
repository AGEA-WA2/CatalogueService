package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.*
import com.example.catalogueservicepart.services.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("products")
class ProductController(val productService: ProductService) {

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    fun addNewProduct(@RequestBody newProduct: ProductBodyDTO): ResponseEntity<*> {
        return productService.createProduct(newProduct)
    }

    @GetMapping
    fun retrieveProductByCategory(@RequestParam("category") category: String?): ResponseEntity<*> {
        return productService.getAllProductsOrByCategory(category)
    }

    @GetMapping("/{productId}")
    fun getProductId(@PathVariable("productId") productId: Long): ResponseEntity<ProductDTO> {
        return productService.getProductById(productId)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{productId}")
    fun updateProductById(@PathVariable("productId") productId: Long, @RequestBody productBodyDTO: ProductBodyDTO): ResponseEntity<ProductDTO> {
        return productService.updateOrCreateProduct(productId, productBodyDTO)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{productId}")
    fun patchProductById(@PathVariable("productId") productId: Long, @RequestBody productPatchDTO: ProductPatchDTO): ResponseEntity<ProductDTO> {
        return productService.updateExistingProduct(productId, productPatchDTO)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable("productId") productId: Long): ResponseEntity<Any> {
        return ResponseEntity(productService.deleteProduct(productId), HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{productId}/picture")
    fun getPicture(@PathVariable("productId") productId: Long): ResponseEntity<PictureUrlDTO?> {
        return productService.getProductPictureUrl(productId)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{productId}/picture")
    fun updatePicture(@PathVariable("productId") productId: Long, @RequestBody pictureUrl: PictureUrlDTO): ResponseEntity<ProductDTO> {
        return productService.updateProductPictureUrl(productId, pictureUrl)
    }

    @GetMapping("/{productId}/warehouses")
    fun getWarehouseByProduct(@PathVariable("productId") productId: Long): ResponseEntity<Array<ProductQuantityDTO>> {
        return productService.getProductWarehouses(productId)
    }
}
