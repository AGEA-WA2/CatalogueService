package com.example.catalogueservicepart.domain

import com.example.catalogueservicepart.roles.Rolename
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(name = "user", indexes = [Index(name = "index", columnList = "username", unique = true)])
class User : EntityBase<Long>() {

    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 20)
    @NotBlank(message = "username may not be blank")
    //username formats: <username> or <user.name> or <user_name> with alphanumeric chars only
    @Pattern(regexp = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*\$")
    var username: String = ""

    var password: String = ""

    @Column(unique = true, nullable = false)
    @NotBlank(message = "email may not be blank")
    //regex for a typical email address
    //@Pattern(regexp = "\\w+@\\w+\\.\\w+(,\\s*\\w+@\\w+\\.\\w+)*")
    @Pattern(regexp = "^([a-z0-9]+([-_.][a-z0-9]+)*)@([a-z0-9]+\\.)+[a-z]{2,}$")
    var email: String = ""

    var address:String? =""

    @Column(columnDefinition = "boolean default false")
    var isEnabled: Boolean = false

    var roles: String = ""

    @OneToOne(mappedBy = "user", targetEntity = EmailVerificationToken::class, cascade = [CascadeType.ALL])
    var token: EmailVerificationToken? = null

    @Column
    var firstName: String = "no-name"

    //@Column(nullable = false)
    @Column
    var lastName: String = "no-name"

    fun addRole(role: Rolename) {
        val newRole = role.name
        if (newRole !in roles) {
            if (roles.isBlank())
                roles = newRole
            else
                roles += ", $newRole"
        }
    }

    fun removeRole(role: Rolename) {
        val oldRole = role.name
        var updatedRoles = ""
        if (oldRole in roles) {
            roles.split(", ").forEach { r ->
                if (r != oldRole) {
                    updatedRoles += if (updatedRoles.isBlank()) r else ", $r"
                }
            }
            roles = updatedRoles
        }
    }

    fun retrieveRoles(): Set<Rolename> {
        val result = mutableSetOf<Rolename>()
        roles.split(", ").toSet().forEach {
            if (it == "CUSTOMER") result.add(Rolename.CUSTOMER)
            if (it == "ADMIN") result.add(Rolename.ADMIN)
        }
        return result
    }

}
