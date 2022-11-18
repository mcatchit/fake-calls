package com.alenskaya.fakecalls.presentation.feature.calls

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.alenskaya.fakecalls.presentation.feature.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.presentation.feature.calls.ui.CompletedCallsList
import com.alenskaya.fakecalls.presentation.feature.calls.ui.ScheduledCallsList
import com.alenskaya.fakecalls.presentation.components.LoadingProgress
import com.alenskaya.fakecalls.presentation.components.MainTitle

/**
 * Calls screen ui
 */
@Composable
fun CallsScreen(
    viewModel: CallsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.screenState.collectAsState()
    val imageLoader = viewModel.imageLoader

    val editCallAction = { call: CallsScreenCallModel ->
    }
    val repeatCallAction = { call: CallsScreenCallModel ->
    }
    val deleteCallAction = { call: CallsScreenCallModel ->
    }

    CallsScreen(
        state = state,
        imageLoader = imageLoader,
        editCallAction = editCallAction,
        repeatCallAction = repeatCallAction,
        deleteCallAction = deleteCallAction
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
                state.isLoading -> LoadingText()
                state.message != null -> Message(text = state.message)
                else -> CallsLists(
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
private fun LoadingText() {
    PlaceInCenter {
        LoadingProgress(
            text = "Wait a second. Loading calls",
            modifier = Modifier
                .align(Alignment.Center)
        )
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
private fun CallsLists(
    scheduledCalls: List<CallsScreenCallModel>,
    completedCalls: List<CallsScreenCallModel>,
    imageLoader: ImageLoader,
    editCallAction: (CallsScreenCallModel) -> Unit,
    repeatCallAction: (CallsScreenCallModel) -> Unit,
    deleteCallAction: (CallsScreenCallModel) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ScheduledCallsList(
            calls = scheduledCalls,
            modifier = Modifier.fillMaxWidth(),
            imageLoader = imageLoader,
            editCallAction = editCallAction,
            deleteCallAction = deleteCallAction
        )
        CompletedCallsList(
            calls = completedCalls,
            modifier = Modifier.fillMaxWidth(),
            imageLoader = imageLoader,
            repeatCallAction = repeatCallAction,
            deleteCallAction = deleteCallAction
        )
    }
}

@Composable
private fun PlaceInCenter(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp)
    ) {
        content.invoke(this)
    }
}
