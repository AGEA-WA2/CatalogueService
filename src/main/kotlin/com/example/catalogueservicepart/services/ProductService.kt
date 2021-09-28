package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.*
import com.sun.mail.iap.Response
import org.springframework.http.ResponseEntity

interface ProductService {
    fun createProduct(product: ProductBodyDTO): ResponseEntity<ProductDTO>
    fun getAllProductsOrByCategory(category: String?): ResponseEntity<Array<ProductDTO>>
    fun getProductById(id: Long): ResponseEntity<ProductDTO>
    fun updateOrCreateProduct(id: Long, product: ProductBodyDTO): ResponseEntity<ProductDTO>
    fun updateExistingProduct(id: Long, product: ProductPatchDTO): ResponseEntity<ProductDTO>
    fun deleteProduct(pid: Long)
    fun getProductPictureUrl(pid: Long): ResponseEntity<PictureUrlDTO?>
    fun updateProductPictureUrl(pid: Long, path: PictureUrlDTO): ResponseEntity<ProductDTO>
    fun getProductWarehouses(pid: Long): ResponseEntity<Array<ProductQuantityDTO>>
//    fun getOrderProduct(pid: Long, qnt: Int): OrderDTO?
}