package com.alenskaya.fakecalls.data.local

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError

/**
 * Catches exceptions in database [request].
 * @return result of [request] or [BaseResponse.Error] if an exception was thrown.
 */
internal suspend fun <T> catchExceptions(
    request: suspend () -> BaseResponse<T, DatabaseError>
): BaseResponse<T, DatabaseError> {
    return try {
        request.invoke()
    } catch (e: Exception) {
        BaseResponse.Error(DatabaseError)
    }
}
