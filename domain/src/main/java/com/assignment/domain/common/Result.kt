package com.assignment.domain.common

/**
 * A generic class that holds a value or an error status.
 * @param <T>
 */
sealed class Result<T>(
    val data: T? = null,
    val message: String? = null,
    val errorEntity: ErrorEntity? = null
) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(message: String, data: T? = null, errorEntity: ErrorEntity? = null) :
        Result<T>(data, message, errorEntity)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[message=$message, data=$data, errorEntity=$errorEntity]"
        }
    }
}