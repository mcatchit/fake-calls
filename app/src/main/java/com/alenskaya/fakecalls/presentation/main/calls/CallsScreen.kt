package com.alenskaya.fakecalls.presentation.main.calls

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.presentation.components.FullScreenLoadingMessage
import com.alenskaya.fakecalls.presentation.components.MainTitle
import com.alenskaya.fakecalls.presentation.components.FullScreenMessage
import com.alenskaya.fakecalls.presentation.main.calls.model.CallType
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenListItem
import com.alenskaya.fakecalls.presentation.main.calls.ui.CallTypeToHeaderTitleConverter
import com.alenskaya.fakecalls.presentation.main.calls.ui.CallsList
import com.alenskaya.fakecalls.presentation.main.calls.ui.CompletedCallRowBuilder
import com.alenskaya.fakecalls.presentation.main.calls.ui.CallGroupHeaderBuilder
import com.alenskaya.fakecalls.presentation.main.calls.ui.CallsListFactory
import com.alenskaya.fakecalls.presentation.main.calls.ui.ScheduledCallRowBuilder

/**
 * Calls screen ui.
 */
@Composable
fun CallsScreen(
    viewModel: CallsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.screenState.collectAsState()
    val imageLoader = viewModel.imageLoader

    CallsScreen(
        state = state,
        imageLoader = imageLoader,
        callsStrings = viewModel.callsStrings,
        editCallAction = { call: CallsScreenCallModel ->
            viewModel.sendEvent(CallsScreenEvent.EditCall(call.id))
        },
        repeatCallAction = { call: CallsScreenCallModel ->
            viewModel.sendEvent(CallsScreenEvent.RepeatCall(call.id))
        },
        deleteCallAction = { call: CallsScreenCallModel, type: CallType ->
            viewModel.sendEvent(CallsScreenEvent.DeleteCall(call, type))
        }
    )
}

@Composable
private fun CallsScreen(
    state: CallsScreenState,
    imageLoader: ImageLoader,
    callsStrings: CallsStrings,
    editCallAction: (CallsScreenCallModel) -> Unit,
    repeatCallAction: (CallsScreenCallModel) -> Unit,
    deleteCallAction: (CallsScreenCallModel, CallType) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column {
            MainTitle(
                text = callsStrings.title(),
                modifier = Modifier.padding(24.dp)
            )
            when {
                state.isLoading && state.areCallsEmpty() -> FullScreenLoadingMessage(callsStrings.loadingMessage())
                state.message != null -> FullScreenMessage(text = state.message)
                else -> ListOfCalls(
                    scheduledCalls = state.scheduledCalls,
                    completedCalls = state.completedCalls,
                    imageLoader = imageLoader,
                    callsStrings = callsStrings,
                    editCallAction = editCallAction,
                    editDescription = callsStrings.editButtonDescription(),
                    repeatCallAction = repeatCallAction,
                    repeatDescription = callsStrings.repeatButtonDescription(),
                    deleteCallAction = deleteCallAction,
                    deleteDescription = callsStrings.deleteButtonDescription()
                )
            }
        }
    }
}

@Composable
private fun ListOfCalls(
    scheduledCalls: List<CallsScreenCallModel>,
    completedCalls: List<CallsScreenCallModel>,
    imageLoader: ImageLoader,
    callsStrings: CallsStrings,
    editCallAction: (CallsScreenCallModel) -> Unit,
    editDescription: String,
    repeatCallAction: (CallsScreenCallModel) -> Unit,
    repeatDescription: String,
    deleteCallAction: (CallsScreenCallModel, CallType) -> Unit,
    deleteDescription: String
) {
    var isScheduledCallsOpened by remember { mutableStateOf(true) }
    var isCompletedCallsOpened by remember { mutableStateOf(true) }

    val titleConverter = CallTypeToHeaderTitleConverter(callsStrings)
    val callsListItems = listOf<CallsScreenListItem>()
        .addCallsGroup(
            titleConverter.convert(CallType.SCHEDULED),
            scheduledCalls,
            CallType.SCHEDULED,
            isScheduledCallsOpened
        )
        .addCallsGroup(
            titleConverter.convert(CallType.COMPLETED),
            completedCalls,
            CallType.COMPLETED,
            isCompletedCallsOpened
        )

    val callGroupHeaderBuilders = mapOf(
        CallType.SCHEDULED to CallGroupHeaderBuilder {
            isScheduledCallsOpened = !isScheduledCallsOpened
        },
        CallType.COMPLETED to CallGroupHeaderBuilder {
            isCompletedCallsOpened = !isCompletedCallsOpened
        }
    )

    val callRowBuilders = mapOf(
        CallType.SCHEDULED to ScheduledCallRowBuilder(
            imageLoader = imageLoader,
            editCallAction = editCallAction,
            editDescription = editDescription,
            deleteCallAction = { call ->
                deleteCallAction(call, CallType.SCHEDULED)
            },
            deleteDescription = deleteDescription
        ),
        CallType.COMPLETED to CompletedCallRowBuilder(
            imageLoader = imageLoader,
            repeatCallAction = repeatCallAction,
            repeatDescription = repeatDescription,
            deleteCallAction = { call ->
                deleteCallAction(call, CallType.COMPLETED)
            },
            deleteDescription = deleteDescription
        )
    )

    CallsList(
        items = callsListItems,
        callsListFactory = CallsListFactory(callGroupHeaderBuilders, callRowBuilders)
    )
}

private fun List<CallsScreenListItem>.addCallsGroup(
    title: String,
    calls: List<CallsScreenCallModel>,
    type: CallType,
    isOpened: Boolean
): List<CallsScreenListItem> {
    return this.toMutableList().apply {
        if (calls.isNotEmpty()) {
            add(
                CallsScreenListItem.Header(
                    title = title,
                    isOpened = isOpened,
                    type = type
                )
            )

            if (isOpened) {
                calls.forEach { call ->
                    add(CallsScreenListItem.CallItem(call, type))
                }
            }
        }
    }
}
