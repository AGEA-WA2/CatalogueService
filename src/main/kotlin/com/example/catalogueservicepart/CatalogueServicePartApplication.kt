package com.example.catalogueservicepart

import com.netflix.appinfo.ApplicationInfoManager
import com.netflix.discovery.DiscoveryClient
import com.netflix.discovery.DiscoveryManager
//import org.springframework.cloud.client.discovery.DiscoveryClient
import com.netflix.discovery.EurekaClient
import com.netflix.discovery.EurekaClientConfig
import com.netflix.discovery.guice.EurekaModule
import com.netflix.discovery.shared.transport.EurekaClientFactoryBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean
import org.springframework.context.annotation.Bean
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.client.RestTemplate

@EnableEurekaClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableScheduling
class CatalogueServicePartApplication{
    @Value("\${spring.mail.host}")
    lateinit var host: String

    @Value("\${spring.mail.port}")
    var port: Int = 0

    @Value("\${spring.mail.username}")
    lateinit var username: String

    @Value("\${spring.mail.password}")
    lateinit var password: String

    @Value("\${spring.mail.properties.mail.smtp.auth}")
    lateinit var smtpAuth: String

    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    lateinit var startTls: String

    @Value("\${spring.mail.properties.mail.debug}")
    lateinit var mailDebug: String

    @Bean(name = ["mailSender"])
    fun getMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = host
        mailSender.port = port
        mailSender.username = username
        mailSender.password = password

        val properties = mailSender.javaMailProperties
        properties["mail.transport.protocol"] = "smtp"
        properties["mail.smtp.auth"] = smtpAuth
        properties["mail.smtp.starttls.enable"] = startTls
        properties["mail.debug"] = mailDebug

        return mailSender
    }
    @Bean
    fun passwordEncode(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
    @Bean
    fun restTemplate(): RestTemplate = RestTemplateBuilder().build()
    //@Bean
    //fun discoveryClient(): DiscoveryClient = DiscoveryManager.getInstance().discoveryClient
    //fun discoveryClient(): DiscoveryClient =
}

fun main(args: Array<String>) {
    runApplication<CatalogueServicePartApplication>(*args)
}
