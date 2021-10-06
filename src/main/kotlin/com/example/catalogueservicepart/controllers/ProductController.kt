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
        return ResponseEntity(productService.createProduct(newProduct),HttpStatus.OK)
    }

    @GetMapping
    fun retrieveProductByCategory(@RequestParam("category") category: String?): ResponseEntity<*> {
        return ResponseEntity(productService.getAllProductsOrByCategory(category),HttpStatus.OK)
    }

    //TODO Ho cambiato ResponseEntity<ProductDTO> in ResponseEntity<*> in tutte le chiamate perchè dava problemi
    @GetMapping("/{productId}")
    //fun getProductId(@PathVariable("productId") productId: Long): ResponseEntity<ProductDTO>
    fun getProductId(@PathVariable("productId") productId: Long): ResponseEntity<*>{
        return ResponseEntity(productService.getProductById(productId),HttpStatus.OK)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{productId}")
    //fun updateProductById(@PathVariable("productId") productId: Long, @RequestBody productBodyDTO: ProductBodyDTO): ResponseEntity<ProductDTO>
    fun updateProductById(@PathVariable("productId") productId: Long, @RequestBody productBodyDTO: ProductBodyDTO): ResponseEntity<*>{
        return ResponseEntity(productService.updateOrCreateProduct(productId, productBodyDTO),HttpStatus.OK)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{productId}")
    //fun patchProductById(@PathVariable("productId") productId: Long, @RequestBody productPatchDTO: ProductPatchDTO): ResponseEntity<ProductDTO>
    fun patchProductById(@PathVariable("productId") productId: Long, @RequestBody productPatchDTO: ProductPatchDTO): ResponseEntity<*>{
        return ResponseEntity(productService.updateExistingProduct(productId, productPatchDTO),HttpStatus.OK)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable("productId") productId: Long): ResponseEntity<Any> {
        return ResponseEntity(productService.deleteProduct(productId), HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{productId}/picture")
    //fun getPicture(@PathVariable("productId") productId: Long): ResponseEntity<PictureUrlDTO?>
    fun getPicture(@PathVariable("productId") productId: Long): ResponseEntity<*> {
        return ResponseEntity(productService.getProductPictureUrl(productId),HttpStatus.OK)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{productId}/picture")
    //fun updatePicture(@PathVariable("productId") productId: Long, @RequestBody pictureUrl: PictureUrlDTO): ResponseEntity<ProductDTO>
    fun updatePicture(@PathVariable("productId") productId: Long, @RequestBody pictureUrl: PictureUrlDTO): ResponseEntity<*>{
        return ResponseEntity(productService.updateProductPictureUrl(productId, pictureUrl),HttpStatus.OK)
    }

    @GetMapping("/{productId}/warehouses")
    //fun getWarehouseByProduct(@PathVariable("productId") productId: Long): ResponseEntity<Array<ProductQuantityDTO>>
    fun getWarehouseByProduct(@PathVariable("productId") productId: Long): ResponseEntity<*> {
        return ResponseEntity(productService.getProductWarehouses(productId),HttpStatus.OK)
    }
}
