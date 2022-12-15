package com.alenskaya.fakecalls.presentation.main.create.converter

import com.alenskaya.fakecalls.presentation.main.create.CreateCallScreenMode
import com.alenskaya.fakecalls.presentation.main.create.CreateStrings
import com.alenskaya.fakecalls.presentation.main.create.model.CreateCallScreenFormLabels
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts create call screen mode to labels
 */
class CreateCallScreenModeToLabelsConverter(
    private val createStrings: CreateStrings
) :
    Converter<CreateCallScreenMode, CreateCallScreenFormLabels> {

    override fun convert(input: CreateCallScreenMode) = CreateCallScreenFormLabels(
        title = getTitle(input),
        buttonText = getButtonText(input)
    )

    private fun getTitle(mode: CreateCallScreenMode): String {
        return when (mode) {
            is CreateCallScreenMode.CreateCustom -> createStrings.createNewTitle()
            is CreateCallScreenMode.CreateFake -> createStrings.createNewTitle()
            is CreateCallScreenMode.Edit -> createStrings.editCallTitle()
            is CreateCallScreenMode.Repeat -> createStrings.repeatCallTitle()
        }
    }

    private fun getButtonText(mode: CreateCallScreenMode): String {
        return when (mode) {
            is CreateCallScreenMode.CreateCustom -> createStrings.createButton()
            is CreateCallScreenMode.CreateFake -> createStrings.createButton()
            is CreateCallScreenMode.Edit -> createStrings.saveButton()
            is CreateCallScreenMode.Repeat -> createStrings.saveButton()
        }
    }
}
