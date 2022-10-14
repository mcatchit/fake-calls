package com.alenskaya.fakecalls.domain

/**
 * Reason why remote request was executed with exception
 */
sealed class RemoteRequestErrorCause {
    object NoInternetConnection: RemoteRequestErrorCause()
    object BadRequest: RemoteRequestErrorCause()
    object Unknown: RemoteRequestErrorCause()
}
