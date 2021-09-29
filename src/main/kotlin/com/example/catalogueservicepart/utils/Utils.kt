package com.example.catalogueservicepart.utils

import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class Utils {

    @Qualifier("eurekaClient")
    @Autowired
    lateinit var eurekaClient: EurekaClient

    fun buildUrl(nameService: String): String{
        val addr = eurekaClient.getApplication(nameService).instances[0]
        return "http://${addr.ipAddr}:${addr.port}"
    }
}
