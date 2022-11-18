package com.alenskaya.fakecalls.data.remote.contacts

import com.alenskaya.fakecalls.util.Converter
import com.alenskaya.fakecalls.data.remote.contacts.api.dto.FakeContactResponseDto
import com.alenskaya.fakecalls.domain.contacts.model.RemoteFakeContactsResponse

/**
 * Converts api response(dto) to fake contact data request to domain response.
 */
class FakeContactResponseDtoToDomainResponseConverter :
    Converter<FakeContactResponseDto, RemoteFakeContactsResponse> {

    override fun convert(input: FakeContactResponseDto): RemoteFakeContactsResponse {
        return RemoteFakeContactsResponse(
            contacts = input.results.map { fakeUserDto ->
                FakeContactDtoToDomainModelConverter.convert(fakeUserDto)
            }
        )
    }
}
