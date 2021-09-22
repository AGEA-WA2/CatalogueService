package com.example.catalogueservicepart.domain

import javax.persistence.*

@Entity
class Customer : EntityBase<Long>() {

    //@Column(nullable = false)
    @Column
    var firstName: String = "no-name"

    //@Column(nullable = false)
    @Column
    var lastName: String = "no-name"

    var deliveryAddress: String? = ""

    @OneToMany(mappedBy = "customer", targetEntity = Wallet::class, cascade = [CascadeType.ALL])
    var wallets: MutableSet<Wallet?> = mutableSetOf()

    @OneToOne
    var user: User? = null
}