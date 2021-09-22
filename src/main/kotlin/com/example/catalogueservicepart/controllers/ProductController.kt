package com.example.catalogueservicepart.controllers

import com.example.catalogueservicepart.dto.ProductBodyDTO
import com.example.catalogueservicepart.dto.ProductPatchDTO
import com.example.catalogueservicepart.security.JwtUtils
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("products")
class ProductController(val jwtUtils: JwtUtils,) {

    lateinit var authenticationManager: AuthenticationManager

    @PreAuthorize("ADMIN")
    @PostMapping
    fun addNewProduct(@RequestBody newProduct:ProductBodyDTO){

    }

    @GetMapping
    fun retreiveProductByCategory(@RequestParam("category")category:String){

    }

    @GetMapping("/{productId}")
    fun getProductId(@PathVariable("productId") productId:Long){

    }

    @PreAuthorize("ADMIN")
    @PutMapping("/{productId}")
    fun updateProductById(@PathVariable("productId")productId: Long, @RequestBody productBodyDTO: ProductBodyDTO){

    }

    @PreAuthorize("ADMIN")
    @PatchMapping("/{productId}")
    fun pathProductById(@PathVariable("productId")productId: Long,@RequestBody productPatchDTO: ProductPatchDTO){

    }

    @PreAuthorize("ADMIN")
    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable("productId")productId: Long){

    }

    @GetMapping("/{productId}/picture")
    fun getPicture(@PathVariable("productId")productId: Long){

    }

    @PreAuthorize("ADMIN")
    @PostMapping("/{productId}/picture")
    fun updatePicture(@PathVariable("productId")productId: Long,@RequestBody pictureUrl:String){

    }

    @GetMapping("/{productId}/warehouse")
    fun getWarehouseByProduct(@PathVariable("productId")productId: Long){

    }
}