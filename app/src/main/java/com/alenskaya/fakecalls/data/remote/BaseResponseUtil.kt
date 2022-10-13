package com.alenskaya.fakecalls.data.remote

import com.alenskaya.fakecalls.domain.BaseResponse
import retrofit2.Response

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
) = BaseResponse(
    code = code(),
    message = message(),
    payload = body()?.let { dto ->
        dtoToPayloadConverter.convert(dto)
    }
)
