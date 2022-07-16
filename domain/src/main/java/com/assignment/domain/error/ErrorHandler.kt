package com.assignment.domain.error

import com.assignment.domain.common.ErrorEntity

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}