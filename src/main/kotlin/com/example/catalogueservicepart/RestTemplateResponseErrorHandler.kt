package com.example.catalogueservicepart

import javassist.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.ResponseErrorHandler

@Component
class RestTemplateResponseErrorHandler : ResponseErrorHandler {
    override fun hasError(response: ClientHttpResponse): Boolean {
        println("DENTRO HASERROR")
        return (response.statusCode.is5xxServerError || response.statusCode.is4xxClientError)
    }

    override fun handleError(httpResponse: ClientHttpResponse) {
        println("DENTRO HANDLEHERRROR")
        println("ciao")
    }
/*        if (httpResponse.statusCode()
                .series() === HttpStatus.Series.SERVER_ERROR
        ) {
            // handle SERVER_ERROR
        } else if (httpResponse.getStatusCode().series() === HttpStatus.Series.CLIENT_ERROR) {
            // handle CLIENT_ERROR
            if (httpResponse.getStatusCode() === HttpStatus.NOT_FOUND) {
                throw NotFoundException()
            }
        }
    }*/

}
