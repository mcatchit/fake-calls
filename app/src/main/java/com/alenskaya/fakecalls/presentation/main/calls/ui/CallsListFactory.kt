package com.alenskaya.fakecalls.presentation.main.calls.ui

import androidx.compose.runtime.Composable
import com.alenskaya.fakecalls.presentation.main.calls.model.CallType
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenListItem

/**
 * Factory for producing elements of calls list.
 * @property callGroupHeaderBuilders - header builders mapped to their types.
 * @property callRowBuilders - row builders mapped to their types.
 */
class CallsListFactory(
    private val callGroupHeaderBuilders: Map<CallType, CallGroupHeaderBuilder>,
    private val callRowBuilders: Map<CallType, CallRowBuilder>
) {

    /**
     * Returns composable function which builds header.
     * @param header - data for building header.
     */
    fun createHeaderRow(header: CallsScreenListItem.Header): @Composable () -> Unit {
        return getHeaderBuilder(header.type).buildHeader(header)
    }

    /**
     * Returns composable function which builds call row.
     * @param call - data for building call.
     */
    fun createCallRow(call: CallsScreenListItem.CallItem): @Composable () -> Unit {
        return getCallRowBuilder(call.type).buildRow(call.callModel)
    }

    private fun getHeaderBuilder(callType: CallType): CallGroupHeaderBuilder {
        return callGroupHeaderBuilders[callType]
            ?: error("Header builder for type $callType was not provided")
    }

    private fun getCallRowBuilder(callType: CallType): CallRowBuilder {
        return callRowBuilders[callType]
            ?: error("Call row builder for type $callType was not provided")
    }
}
