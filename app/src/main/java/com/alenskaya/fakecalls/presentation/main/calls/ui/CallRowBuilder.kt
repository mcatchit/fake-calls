package com.alenskaya.fakecalls.presentation.main.calls.ui

import androidx.compose.runtime.Composable
import coil.ImageLoader
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.presentation.main.calls.ui.row.CompletedCallRow
import com.alenskaya.fakecalls.presentation.main.calls.ui.row.ScheduledCallRow

/**
 * Interface of call row builders, which build rows according to passed data.
 */
interface CallRowBuilder {

    /**
     * Returns composable function which builds a row for [call].
     */
    fun buildRow(call: CallsScreenCallModel): @Composable () -> Unit
}

/**
 * Call row builder of scheduled calls.
 * @property imageLoader - application image loader.
 * @property editCallAction - action called when user clicks edit button.
 * @property deleteCallAction - action called when user clicks delete button.
 */
class ScheduledCallRowBuilder(
    private val imageLoader: ImageLoader,
    private val editCallAction: (CallsScreenCallModel) -> Unit,
    private val editDescription: String,
    private val deleteCallAction: (CallsScreenCallModel) -> Unit,
    private val deleteDescription: String
) : CallRowBuilder {

    override fun buildRow(call: CallsScreenCallModel): @Composable () -> Unit {
        return {
            ScheduledCallRow(
                call = call,
                editCallAction = editCallAction,
                editDescription = editDescription,
                deleteCallAction = deleteCallAction,
                deleteDescription = deleteDescription,
                imageLoader = imageLoader
            )
        }
    }
}

/**
 * Call row builder of completed calls.
 * @property imageLoader - application image loader.
 * @property repeatCallAction - action called when user clicks repeat button.
 * @property deleteCallAction - action called when user clicks delete button.
 */
class CompletedCallRowBuilder(
    private val imageLoader: ImageLoader,
    private val repeatCallAction: (CallsScreenCallModel) -> Unit,
    private val repeatDescription: String,
    private val deleteCallAction: (CallsScreenCallModel) -> Unit,
    private val deleteDescription: String
) : CallRowBuilder {

    override fun buildRow(call: CallsScreenCallModel): @Composable () -> Unit {
        return {
            CompletedCallRow(
                call = call,
                repeatCallAction = repeatCallAction,
                repeatDescription = repeatDescription,
                deleteCallAction = deleteCallAction,
                deleteDescription = deleteDescription,
                imageLoader = imageLoader
            )
        }
    }
}
