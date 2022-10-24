package com.alenskaya.fakecalls.presentation.home.model

import com.alenskaya.fakecalls.domain.contacts.model.FakeContact

/**
 * Ui model of fake contact (wrapper for [FakeContact]).
 * Has two additional properties - [id] and [isHintVisible].
 *
 * @property id - unique identifier of contact. TODO remove in future?
 * @property contact - contact value.
 * @property isHintVisible - visibility of hint.
 */
data class HomeScreenFakeContactModel(
    val id: Int,
    val contact: FakeContact,
    val isHintVisible: Boolean = false
)
