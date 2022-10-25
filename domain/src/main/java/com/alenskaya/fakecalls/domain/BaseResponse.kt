package com.alenskaya.fakecalls.domain

/**
 * Base response from repository.
 * @param T - type of payload.
 */
sealed class BaseResponse<T> {

    /**
     * Request was executed successfully
     */
    data class Success<T>(
        val payload: T
    ) : BaseResponse<T>()

    /**
     * Request was executed with exception
     */
    data class Error<T>(
        val cause: RemoteRequestErrorCause
    ) : BaseResponse<T>()
}
