package com.alenskaya.fakecalls.presentation.main.calls

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.presentation.components.FullScreenLoadingMessage
import com.alenskaya.fakecalls.presentation.components.MainTitle
import com.alenskaya.fakecalls.presentation.components.PlaceInCenter
import com.alenskaya.fakecalls.presentation.main.calls.model.CallType
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenListItem
import com.alenskaya.fakecalls.presentation.main.calls.ui.CallTypeToHeaderTitleConverter
import com.alenskaya.fakecalls.presentation.main.calls.ui.CallsList
import com.alenskaya.fakecalls.presentation.main.calls.ui.CompletedCallRowBuilder
import com.alenskaya.fakecalls.presentation.main.calls.ui.CallGroupHeaderBuilder
import com.alenskaya.fakecalls.presentation.main.calls.ui.CallsListFactory
import com.alenskaya.fakecalls.presentation.main.calls.ui.ScheduledCallRowBuilder

/**
 * Calls screen ui
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
        editCallAction = {},
        repeatCallAction = { call: CallsScreenCallModel ->
            viewModel.sendEvent(CallsScreenEvent.RepeatCall(call.id))
        },
        deleteCallAction = { call: CallsScreenCallModel ->
            viewModel.sendEvent(CallsScreenEvent.DeleteCall(call.id, true))
        }
    )
}

@Composable
private fun CallsScreen(
    state: CallsScreenState,
    imageLoader: ImageLoader,
    editCallAction: (CallsScreenCallModel) -> Unit,
    repeatCallAction: (CallsScreenCallModel) -> Unit,
    deleteCallAction: (CallsScreenCallModel) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column {
            MainTitle(
                text = "My calls", //FIXME
                modifier = Modifier.padding(24.dp)
            )
            when {
                state.isLoading && state.areCallsEmpty() -> FullScreenLoadingMessage("Wait a second. Loading calls") //FIXME
                state.message != null -> Message(text = state.message)
                else -> ListOfCalls(
                    scheduledCalls = state.scheduledCalls,
                    completedCalls = state.completedCalls,
                    imageLoader = imageLoader,
                    editCallAction = editCallAction,
                    repeatCallAction = repeatCallAction,
                    deleteCallAction = deleteCallAction
                )
            }
        }
    }
}

@Composable
private fun Message(text: String) {
    PlaceInCenter {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3.copy(color = MaterialTheme.colors.secondaryVariant),
            modifier = Modifier
                .width(120.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun ListOfCalls(
    scheduledCalls: List<CallsScreenCallModel>,
    completedCalls: List<CallsScreenCallModel>,
    imageLoader: ImageLoader,
    editCallAction: (CallsScreenCallModel) -> Unit,
    repeatCallAction: (CallsScreenCallModel) -> Unit,
    deleteCallAction: (CallsScreenCallModel) -> Unit
) {
    var isScheduledCallsOpened by remember { mutableStateOf(true) }
    var isCompletedCallsOpened by remember { mutableStateOf(true) }

    val callsListItems = listOf<CallsScreenListItem>()
        .addCallsGroup(scheduledCalls, CallType.SCHEDULED, isScheduledCallsOpened)
        .addCallsGroup(completedCalls, CallType.COMPLETED, isCompletedCallsOpened)

    val callGroupHeaderBuilders = mapOf(
        CallType.SCHEDULED to CallGroupHeaderBuilder { isScheduledCallsOpened = !isScheduledCallsOpened },
        CallType.COMPLETED to CallGroupHeaderBuilder { isCompletedCallsOpened = !isCompletedCallsOpened }
    )

    val callRowBuilders = mapOf(
        CallType.SCHEDULED
                to ScheduledCallRowBuilder(imageLoader, editCallAction, deleteCallAction),
        CallType.COMPLETED
                to CompletedCallRowBuilder(imageLoader, repeatCallAction, deleteCallAction)
    )

    CallsList(
        items = callsListItems,
        callsListFactory = CallsListFactory(callGroupHeaderBuilders, callRowBuilders)
    )
}

private fun List<CallsScreenListItem>.addCallsGroup(
    calls: List<CallsScreenCallModel>,
    type: CallType,
    isOpened: Boolean
): List<CallsScreenListItem> {
    return this.toMutableList().apply {
        if (calls.isNotEmpty()) {
            add(
                CallsScreenListItem.Header(
                    title = CallTypeToHeaderTitleConverter().convert(type),
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
