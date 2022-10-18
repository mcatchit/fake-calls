package com.alenskaya.fakecalls.data.remote

import com.alenskaya.fakecalls.utils.Converter
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.RemoteRequestErrorCause
import retrofit2.Response

/**
 * Catches exceptions in remote [request] and converts them to the cause.
 * @return result of [request] or [BaseResponse.Error] if an exception was thrown.
 */
internal suspend fun <T> catchExceptions(request: suspend () -> BaseResponse<T>): BaseResponse<T> {
    return try {
        request.invoke()
    } catch (e: Exception) {
        BaseResponse.Error(RemoteExceptionToCauseConverter().convert(e))
    }
}

/**
 * Converts retrofit [Response] to [BaseResponse].
 *
 * @param dtoToPayloadConverter - converter from dto to response payload.
 *
 * @param Dto - type of Data Transfer Object.
 * @param Payload - type of returning payload.
 */
internal fun <Dto, Payload> Response<Dto>.toBaseResponse(
    dtoToPayloadConverter: Converter<Dto, Payload>
): BaseResponse<Payload> {
    val dto = body()
    return if (dto != null) {
        BaseResponse.Success(dtoToPayloadConverter.convert(dto))
    } else {
        BaseResponse.Error(RemoteRequestErrorCause.BadResponse(code()))
    }
}
