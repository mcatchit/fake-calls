package com.alenskaya.fakecalls.domain

/**
 * Base response from repository.
 * @param T - type of payload.
 */
sealed class BaseResponse<T, E : ErrorCause> {

    /**
     * Request was executed successfully
     */
    data class Success<T, E : ErrorCause>(
        val payload: T
    ) : BaseResponse<T, E>()

    /**
     * Request was executed with exception
     */
    data class Error<T, E : ErrorCause>(
        val cause: E
    ) : BaseResponse<T, E>()
}
