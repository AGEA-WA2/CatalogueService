package com.example.catalogueservicepart.repositories

import com.example.catalogueservicepart.domain.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepository:CrudRepository<Customer,Long> {
}