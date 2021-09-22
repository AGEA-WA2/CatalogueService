package com.example.catalogueservicepart.domain

import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class Transaction: EntityBase<Long>() {

    @Min(0)
    var money: Double = 0.0

    @Temporal(TemporalType.TIMESTAMP)
    lateinit var time: Date

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var walletSrc: Wallet

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var walletDst: Wallet
}