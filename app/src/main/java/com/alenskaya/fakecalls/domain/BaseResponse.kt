package com.alenskaya.fakecalls.domain

/**
 * Base response from repository.
 * @param T - type of payload.
 */
data class BaseResponse<T> (

    /**
     * Http response code.
     */
    val code: Int,

    /**
     * Http response message.
     */
    val message : String,

    /**
     * Response payload.
     */
    val payload : T? = null
)
