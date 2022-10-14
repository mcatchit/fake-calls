package com.alenskaya.fakecalls.data.remote

import com.alenskaya.fakecalls.utils.Converter
import com.alenskaya.fakecalls.domain.RemoteRequestErrorCause
import com.squareup.moshi.JsonDataException
import java.io.IOException

/**
 * Converts an exception thrown while executing a remote request to [RemoteRequestErrorCause]
 */
class RemoteExceptionToCauseConverter : Converter<Exception, RemoteRequestErrorCause> {
    override fun convert(input: Exception): RemoteRequestErrorCause {
        return when (input) {
            is IOException -> RemoteRequestErrorCause.NoInternetConnection
            is JsonDataException -> RemoteRequestErrorCause.BadRequest
            else -> RemoteRequestErrorCause.Unknown
        }
    }
}
