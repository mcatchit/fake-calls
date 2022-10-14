package com.alenskaya.fakecalls.domain.contacts.model

sealed class FakeContactsResult {
    class Success(val contacts: List<FakeContact>): FakeContactsResult()
    object Error: FakeContactsResult()
}
