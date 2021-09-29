package com.example.catalogueservicepart.utils

import com.fasterxml.jackson.databind.ObjectMapper
import javassist.NotFoundException
import org.springframework.beans.TypeMismatchException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.*
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.transaction.TransactionSystemException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClientException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.sql.SQLException
import java.util.function.Consumer
import javax.validation.ConstraintViolationException


@ControllerAdvice
class MyExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.info(ex.javaClass.name)

        val errors: MutableList<String> = ArrayList()
        for (error in ex.bindingResult.fieldErrors) {
            errors.add(error.field + ": " + error.defaultMessage)
        }
        for (error in ex.bindingResult.globalErrors) {
            errors.add(error.objectName + ": " + error.defaultMessage)
        }
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, errors)
        return handleExceptionInternal(ex, apiError, headers, apiError.status!!, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.info(ex.javaClass.name)
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, ex.rootCause.toString())
        return handleExceptionInternal(ex, apiError, headers, apiError.status!!, request)
    }

    override fun handleBindException(
        ex: BindException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.info(ex.javaClass.name)
        //
        val errors: MutableList<String> = ArrayList()
        for (error in ex.bindingResult.fieldErrors) {
            errors.add(error.field + ": " + error.defaultMessage)
        }
        for (error in ex.bindingResult.globalErrors) {
            errors.add(error.objectName + ": " + error.defaultMessage)
        }
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, errors)
        return handleExceptionInternal(ex, apiError, headers, apiError.status!!, request)
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.info(ex.javaClass.name)
        //
        val error: String = ex.value
            .toString() + " value for " + ex.propertyName + " should be of type " + ex.requiredType
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    override fun handleMissingServletRequestPart(
        ex: MissingServletRequestPartException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.info(ex.javaClass.name)
        //
        val error = ex.requestPartName + " part is missing"
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.info(ex.javaClass.name)
        //
        val error = ex.parameterName + " parameter is missing"
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(
        ex: MethodArgumentTypeMismatchException,
        request: WebRequest?
    ): ResponseEntity<Any?>? {
        logger.info(ex.javaClass.name)
        //
        val error = ex.name + " should be of type " + ex.requiredType!!.name
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException, request: WebRequest?): ResponseEntity<Any?>? {
        logger.info(ex.javaClass.name)
        //
        val errors: MutableList<String> = ArrayList()
        for (violation in ex.constraintViolations) {
            errors.add(violation.rootBeanClass.name + " " + violation.propertyPath + ": " + violation.message)
        }
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, errors)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    // 404
    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.info(ex.javaClass.name)
        //
        val error = "No handler found for " + ex.httpMethod + " " + ex.requestURL
        val apiError = ApiError(HttpStatus.NOT_FOUND, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    // 405
    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.info(ex.javaClass.name)
        //
        val builder = StringBuilder()
        builder.append(ex.method)
        builder.append(" method is not supported for this request. Supported methods are ")
        ex.supportedHttpMethods!!.forEach(Consumer { t: HttpMethod -> builder.append("$t ") })
        val apiError = ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.localizedMessage, builder.toString())
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    // 415
    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.info(ex.javaClass.name)
        //
        val builder = StringBuilder()
        builder.append(ex.contentType)
        builder.append(" media type is not supported. Supported media types are ")
        ex.supportedMediaTypes.forEach(Consumer { t: MediaType ->
            builder.append(
                "$t "
            )
        })
        val apiError =
            ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.localizedMessage, builder.substring(0, builder.length - 2))
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    // 500
    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: WebRequest?): ResponseEntity<Any?>? {
        logger.info(ex.javaClass.name)
        logger.error("error", ex)
        //
        val apiError = ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.localizedMessage, "Error occurred")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    // My Exceptions
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchValueException(ex: NoSuchElementException): ResponseEntity<Any?>? {
        logger.info(ex.javaClass.name)
        val apiError = ApiError(HttpStatus.NOT_FOUND, ex.localizedMessage, "There is not an entity with such an Id")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Any?>? {
        logger.info(ex.javaClass.name)
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, "Argument must not be null")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    @ExceptionHandler(TransactionSystemException::class)
    fun handleTransactionSystem(ex: TransactionSystemException): ResponseEntity<Any?>? {
        logger.info(ex.javaClass.name)
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, ex.rootCause.toString())
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException): ResponseEntity<Any?>? {
        logger.info(ex.javaClass.name)
        val apiError =
            ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, "Resource requested not found or not existing")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    @ExceptionHandler(NullPointerException::class)
    fun handleNullPointer(ex: NullPointerException): ResponseEntity<Any?>? {
        logger.info(ex.javaClass.name)
        val apiError = ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.localizedMessage, "Null pointer reached")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    @ExceptionHandler(SQLException::class)
    fun handleSQL(ex: SQLException): ResponseEntity<Any?>? {
        logger.info(ex.javaClass.name)
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, "SQL constraint violated")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolation(ex: DataIntegrityViolationException): ResponseEntity<Any?>? {
        logger.info(ex.javaClass.name)
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, "SQL constraint violated")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    @ExceptionHandler(RestClientException::class)
    fun handleRestClient(ex: RestClientException): ResponseEntity<Any?>? {
        logger.info(ex.javaClass.name)

        val err = ex as HttpClientErrorException
        val apiError = ObjectMapper().readValue(err.responseBodyAsString, ApiError::class.java)

        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException::class)
    fun handleAccessDenied(ex: org.springframework.security.access.AccessDeniedException): ResponseEntity<Any?>? {
        logger.info(ex.javaClass.name)
        val apiError = ApiError(HttpStatus.UNAUTHORIZED, ex.localizedMessage, "Access denied")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status!!)
    }
}
