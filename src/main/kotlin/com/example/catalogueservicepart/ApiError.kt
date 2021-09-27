package com.example.catalogueservicepart

import org.springframework.http.HttpStatus

class ApiError {
    //
    var status: HttpStatus? = null
    var message: String? = null
    var errors: List<String>? = null

    //
    constructor() : super() {}
    constructor(status: HttpStatus?, message: String?, errors: List<String>?) : super() {
        this.status = status
        this.message = message
        this.errors = errors
    }

    constructor(status: HttpStatus?, message: String?, error: String) : super() {
        this.status = status
        this.message = message
        errors = listOf(error)
    }

    fun setError(error: String) {
        errors = listOf(error)
    }
}
