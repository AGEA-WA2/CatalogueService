package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.dto.*
import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.getForEntity
import org.springframework.web.client.postForEntity

@Service
@Transactional
class ProductServiceImpl(val restTemplate: RestTemplate): ProductService {

    @Qualifier("eurekaClient")
    @Autowired
    lateinit var eurekaClient: EurekaClient

    override fun createProduct(product: ProductBodyDTO): ResponseEntity<ProductDTO> {
        val addr = eurekaClient.getApplication("warehouseService").instances[0]
        return restTemplate.postForEntity("http://${addr.ipAddr}:${addr.port}/products", product, ProductDTO::class.java)
    }

    override fun getAllProductsOrByCategory(category: String?): ResponseEntity<Array<ProductDTO>> {
        val url = "${buildUrl()}?category={category}"
        return restTemplate.getForEntity(url, Array<ProductDTO>::class.java, category)
    }

    override fun getProductById(id: Long): ResponseEntity<ProductDTO> {
        val url = "${buildUrl()}/{id}"
        return restTemplate.getForEntity(url, ProductDTO::class.java, id)
    }

    override fun updateOrCreateProduct(id: Long, product: ProductBodyDTO): ResponseEntity<ProductDTO> {
        val re = RequestEntity.put("${buildUrl()}/{id}", id).accept(MediaType.APPLICATION_JSON).body(product)
        return restTemplate.exchange(re, ProductDTO::class.java)
    }

    override fun updateExistingProduct(id: Long, product: ProductPatchDTO): ResponseEntity<ProductDTO> {
        //val url = "${buildUrl()}/{id}"
        val re = RequestEntity.patch("${buildUrl()}/{id}", id).accept(MediaType.APPLICATION_JSON).body(product)
        return restTemplate.exchange(re, ProductDTO::class.java)
    }

    override fun deleteProduct(pid: Long) {
        return restTemplate.delete("${buildUrl()}/{id}", pid)
    }

    override fun getProductPictureUrl(pid: Long): ResponseEntity<PictureUrlDTO?> {
        return restTemplate.getForEntity("${buildUrl()}/{pid}/picture", pid, PictureUrlDTO::class.java)
    }

    override fun updateProductPictureUrl(pid: Long, path: PictureUrlDTO): ResponseEntity<ProductDTO> {
        val re = RequestEntity.post("${buildUrl()}/{pid}/picture", pid).accept(MediaType.APPLICATION_JSON).body(path)
        //return restTemplate.postForEntity("${buildUrl()}/{pid}/picture", pid, path, ProductDTO::class.java)
        return restTemplate.exchange(re, ProductDTO::class.java)
    }

    override fun getProductWarehouses(pid: Long): ResponseEntity<Array<ProductQuantityDTO>> {
        return restTemplate.getForEntity("${buildUrl()}/{pid}/warehouses", pid, Array<ProductQuantityDTO>::class.java)
    }

    fun buildUrl(): String{
        val addr = eurekaClient.getApplication("warehouseService").instances[0]
        return "http://${addr.ipAddr}:${addr.port}/products"
    }
}