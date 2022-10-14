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
        /**
         * Http response code.
         */
        val code: Int,

        /**
         * Http response message.
         */
        val message: String,

        /**
         * Response payload.
         */
        val payload: T? = null
    ) : BaseResponse<T>()

    /**
     * Request was executed with exception
     */
    data class Error<T>(
        val cause: RemoteRequestErrorCause
    ) : BaseResponse<T>()
}
