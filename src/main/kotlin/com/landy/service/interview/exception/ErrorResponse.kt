package com.landy.service.interview.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.util.*

class ErrorResponse(
    status: HttpStatus,
    val message: String,
    var stackTrace: String? = null
) {

    val code: Int
    val status1: String

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
    val timestamp: Date

    init {
        timestamp = Date()
        code = status.value()
        status1 = status.name
    }
}