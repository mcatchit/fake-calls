package com.alenskaya.fakecalls.presentation.main.create.model

/**
 * Labels values displayed on create call screen
 */
data class CreateCallScreenFormLabels(
    /**
     * Screen title
     */
    val title: String,

    /**
     * Save button text
     */
    val buttonText: String
) {
    companion object {
        fun initial() = CreateCallScreenFormLabels("", "")
    }
}
