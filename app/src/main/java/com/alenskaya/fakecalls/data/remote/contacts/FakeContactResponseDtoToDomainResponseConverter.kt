package com.alenskaya.fakecalls.data.remote.contacts

import com.alenskaya.fakecalls.utils.Converter
import com.alenskaya.fakecalls.data.remote.contacts.api.dto.FakeContactResponseDto
import com.alenskaya.fakecalls.domain.contacts.model.FakeContactsResponse

/**
 * Converts api response(dto) to fake contact data request to domain response.
 */
class FakeContactResponseDtoToDomainResponseConverter :
    Converter<FakeContactResponseDto, FakeContactsResponse> {

    override fun convert(input: FakeContactResponseDto): FakeContactsResponse {
        return FakeContactsResponse(
            contacts = input.results.map { fakeUserDto ->
                FakeContactDtoToDomainModelConverter.convert(fakeUserDto)
            }
        )
    }
}
