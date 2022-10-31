package com.alenskaya.fakecalls.domain

/**
 * Reason why remote request was executed with exception
 */
sealed class RemoteRequestErrorCause: ErrorCause {
    object NoInternetConnection: RemoteRequestErrorCause()
    object BadRequest: RemoteRequestErrorCause()
    class BadResponse(val httpCode: Int): RemoteRequestErrorCause()
    object Unknown: RemoteRequestErrorCause()
}
