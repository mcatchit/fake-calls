package com.alenskaya.fakecalls.presentation.main.create

/**
 * Launch modes of create call screen
 */
sealed class CreateCallScreenMode {
    data class CreateFake(val id: Int) : CreateCallScreenMode()
    object CreateCustom : CreateCallScreenMode()
    data class Edit(val callId: Int) : CreateCallScreenMode()
    data class Repeat(val callId: Int) : CreateCallScreenMode()
}
