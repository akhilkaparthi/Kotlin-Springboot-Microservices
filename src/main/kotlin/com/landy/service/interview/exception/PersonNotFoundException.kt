package com.landy.service.interview.exception

import org.springframework.http.HttpStatus

class PersonNotFoundException(val statusCode: HttpStatus, val reason: String) : Exception()