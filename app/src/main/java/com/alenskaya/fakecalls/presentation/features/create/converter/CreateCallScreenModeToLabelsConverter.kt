package com.alenskaya.fakecalls.presentation.features.create.converter

import com.alenskaya.fakecalls.presentation.features.create.CreateCallScreenMode
import com.alenskaya.fakecalls.presentation.features.create.model.CreateCallScreenFormLabels
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts create call screen mode to labels
 * TODO move texts to strings
 */
class CreateCallScreenModeToLabelsConverter :
    Converter<CreateCallScreenMode, CreateCallScreenFormLabels> {

    override fun convert(input: CreateCallScreenMode) = CreateCallScreenFormLabels(
        title = getTitle(input),
        buttonText = getButtonText(input)
    )

    private fun getTitle(mode: CreateCallScreenMode): String {
        return when (mode) {
            is CreateCallScreenMode.CreateCustom -> "Create new call"
            is CreateCallScreenMode.CreateFake -> "Create new call"
            is CreateCallScreenMode.Edit -> "Edit call"
            is CreateCallScreenMode.Repeat -> "Repeat call"
        }
    }

    private fun getButtonText(mode: CreateCallScreenMode): String {
        return when (mode) {
            is CreateCallScreenMode.CreateCustom -> "Create"
            is CreateCallScreenMode.CreateFake -> "Create"
            is CreateCallScreenMode.Edit -> "Save"
            is CreateCallScreenMode.Repeat -> "Save"
        }
    }
}
