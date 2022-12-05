package com.alenskaya.fakecalls.presentation.main.calls.ui

/**
 * Information about action in call row.
 */
data class CallRowAction(

    /**
     * Resource id of an action icon.
     */
    val iconId: Int,

    /**
     * Button description.
     */
    val description: String,

    /**
     * Action executed by button click
     */
    val onClickAction: () -> Unit,
)
