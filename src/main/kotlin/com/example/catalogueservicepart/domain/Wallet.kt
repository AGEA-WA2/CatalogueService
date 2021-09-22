package com.example.catalogueservicepart.domain

import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class Wallet: EntityBase<Long>() {

    @Min(0)
    var currAmount: Double = 0.0

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var customer: Customer

    @OneToMany(mappedBy = "walletDst", targetEntity = Transaction::class, cascade = [CascadeType.ALL])
    var posTran: MutableSet<Transaction?> = mutableSetOf()

    @OneToMany(mappedBy = "walletSrc", targetEntity = Transaction::class, cascade = [CascadeType.ALL])
    var negTran: MutableSet<Transaction?> = mutableSetOf()

    fun addPosTransaction(transaction: Transaction) {
        transaction.walletDst = this
        posTran.add(transaction)
        currAmount += transaction.money
    }

    fun addNegTransaction(transaction: Transaction) {
        transaction.walletSrc = this
        negTran.add(transaction)
        currAmount -= transaction.money
    }
}