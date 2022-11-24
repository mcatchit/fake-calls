package com.alenskaya.fakecalls.presentation

import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * Service which allows to display standard android fragments dialogs.
 * Subscription on [dialogs] flow should be provided in root application activity.
 * @property externalScope - scope for emitting dialogs
 */
class DialogsDisplayer(
    private val externalScope: CoroutineScope
) {

    val dialogs = MutableSharedFlow<DialogFragment>()

    /**
     * Emits [dialog]
     */
    fun showDialog(
        dialog: DialogFragment
    ) {
        externalScope.launch {
            dialogs.emit(dialog)
        }
    }
}
