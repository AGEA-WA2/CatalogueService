package com.example.catalogueservicepart.services

import com.example.catalogueservicepart.controllers.UserObject
import com.example.catalogueservicepart.domain.Customer
import com.example.catalogueservicepart.domain.User
import com.example.catalogueservicepart.dto.*
import com.example.catalogueservicepart.repositories.CustomerRepository
import com.example.catalogueservicepart.repositories.UserRepository
import com.example.catalogueservicepart.roles.Rolename
import com.example.catalogueservicepart.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import javax.transaction.Transactional

@Service
@Transactional
class UserDetailsServiceImpl(
    val restTemplate: RestTemplate,
    val userRepository: UserRepository,
    val notificationService: NotificationServiceImpl,
    val mailService: MailService,
    val customerRepository: CustomerRepository,
    val utils: Utils
) : UserDetailsService {

    @Autowired
    val passwordEncoder: PasswordEncoder?=null

    fun createUser(userObject: UserObject): UserDetailsDTO {
        if(userRepository.findByUsername(userObject.username)!=null){
            throw Exception("Username already taken")
        }
        if(userRepository.findByEmail(userObject.email)!=null){
            throw Exception("Email already taken")
        }
        val user = User()
        user.username = userObject.username
        user.password = passwordEncoder?.encode(userObject.password).toString()
        user.email = userObject.email
        user.isEnabled = false
        user.addRole(Rolename.CUSTOMER)

        val customer = Customer()
        user.addCustomer(customer)
        userRepository.save(user)

        val emailVerificationToken = notificationService.createEmailVerificationToken(user.username)
        mailService.sendMessage(
            user.email,
            "Confirm your email",
            "/auth/registrationConfirm?token=${emailVerificationToken.token}"
        )

        return user.toUserDetailsDTO()
    }

    override fun loadUserByUsername(username: String?): UserDetailsDTO {
        val user = username?.let { userRepository.findByUsername(it) }
            ?: throw UsernameNotFoundException("Specified username doesn't exist")

        return user.toUserDetailsDTO()
    }

    fun addRole(role: String, username: String): UserDetailsDTO {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Specified username doesn't exist")

        user.addRole(Rolename.valueOf(role.toUpperCase()))
        if (user.customer == null && role == "CUSTOMER"){
            user.addCustomer(Customer())
        }
        userRepository.save(user)
        return user.toUserDetailsDTO()
    }

    fun removeRole(role: String, username: String): UserDetailsDTO {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Specified username doesn't exist")
        user.removeRole(Rolename.valueOf(role.toUpperCase()))
        if(user.customer != null) {
            customerRepository.deleteById(user.customer?.getId()!!)
            user.customer = null
        }

        userRepository.save(user)
        return user.toUserDetailsDTO()
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    fun enableUser(username: String): UserDetailsDTO {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Specified username doesn't exist")

        user.isEnabled = true
        userRepository.save(user)
        return user.toUserDetailsDTO()
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    fun customerToAdmin(username:String){
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Specified username doesn't exist")
        user.roles= Rolename.ADMIN.toString()
        userRepository.save(user)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    fun adminToCustomer(username:String){
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Specified username doesn't exist")
        user.roles= Rolename.CUSTOMER.toString()
        userRepository.save(user)
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    fun disableUser(username: String): UserDetailsDTO {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Specified username doesn't exist")

        user.isEnabled = false
        userRepository.save(user)
        return user.toUserDetailsDTO()
    }

    fun confirmRegistration(username: String): UserDetailsDTO {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Specified username doesn't exist")
        if(!user.isEnabled) {
            user.isEnabled = true
            val wallet = CreateWalletDTO(user.getId()!!, 0.0)

            restTemplate.postForEntity(
                "${utils.buildUrl("walletService")}/wallets",
                wallet,
                WalletDTO::class.java
            )

            userRepository.save(user)
        }
        return user.toUserDetailsDTO()
    }

    fun sendWarningEmail(username:String,message:String){
        val user=userRepository.findByUsername(username)
        if(user!=null){
            mailService.sendMessage(user.email,
                "Warning!","Someone has tried to change your password")
        }

    }

    fun changePassword(username: String,oldPassword:String,newPassword:String){
        val user=userRepository.findByUsername(username)
        if(user!=null){
            if(passwordEncoder?.matches(oldPassword,user.password)!!){
                user.password=passwordEncoder?.encode(newPassword).toString()
                userRepository.save(user)
                mailService.sendMessage(
                    user.email,
                    "Password modified",
                    "Your password has been changed. Please if you not change it contact the admin\n" +
                            "New Password: $newPassword"
                )
            }else{
                mailService.sendMessage(
                    user.email,
                    "Try change password",
                    "Someone tried to change the password, but he/she insert the wrong old password\n" +
                            "If you don't do this, please call the admin"
                )
                throw Exception("Wrong old password")
            }
        }else{
            throw UsernameNotFoundException("Wrong username")
        }

    }

    fun getUsers(): Set<UserDetailsDTO> {
        return userRepository.findAll().map { it.toUserDetailsDTO() }.toSet()
    }

    fun changeUsername(oldUsername: String, newUsername: String): UserDetailsDTO? {
        val user = userRepository.findByUsername(oldUsername) ?: return null
        user.username = newUsername
        userRepository.save(user)
        return user.toUserDetailsDTO()
    }

    fun changeAddress(address: String, username: String): UserDetailsDTO? {
        val user = userRepository.findByUsername(username) ?: return null
        user.address = address
        userRepository.save(user)
        return user.toUserDetailsDTO()
    }
}
