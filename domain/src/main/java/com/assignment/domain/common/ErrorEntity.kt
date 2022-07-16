package com.assignment.domain.common

import com.assignment.domain.base.DataModel

sealed class ErrorEntity : DataModel() {
    object Network : ErrorEntity()

    object NotFound : ErrorEntity()

    object AccessDenied : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()

    object Unknown : ErrorEntity()
}