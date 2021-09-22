package com.example.catalogueservicepart.domain

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class EmailVerificationToken: EntityBase<Long>() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    var expiryDate = Date(Date().time + 86400000)

    @Column(nullable = false)
    @NotBlank(message = "token may not be blank")
    var token = UUID.randomUUID().toString()

    @OneToOne(fetch = FetchType.LAZY)
    lateinit var user: User
}