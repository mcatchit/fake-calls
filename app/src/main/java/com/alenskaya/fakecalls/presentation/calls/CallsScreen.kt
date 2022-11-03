package com.alenskaya.fakecalls.presentation.calls

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alenskaya.fakecalls.presentation.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.presentation.calls.ui.CompletedCallsList
import com.alenskaya.fakecalls.presentation.calls.ui.ScheduledCallsList
import com.alenskaya.fakecalls.presentation.components.MainTitle
import com.alenskaya.fakecalls.presentation.home.HomeScreenViewModel

@Composable
fun CallsScreen(
    viewModel: CallsScreenViewModel = hiltViewModel()
) {
    val imageLoader = viewModel.imageLoader

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column {
            MainTitle(
                text = "My calls",
                modifier = Modifier.padding(24.dp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ScheduledCallsList(
                    calls = scheduledCalls,
                    modifier = Modifier.fillMaxWidth(),
                    imageLoader = imageLoader,
                    editCallAction = { /*TODO*/ },
                    deleteCallAction = { /*TODO*/ }
                )
                CompletedCallsList(
                    calls = scheduledCalls,
                    modifier = Modifier.fillMaxWidth(),
                    imageLoader = imageLoader,
                    repeatCallAction = { /*TODO*/ },
                    deleteCallAction = { /*TODO*/ }
                )
            }
        }
    }
}

val scheduledCalls = listOf(
    CallsScreenCallModel(
        id = 1,
        name = "Patrick Weber",
        phone = "+4890856743",
        day = "03.12",
        time = "11:12",
        photoUrl = "https://randomuser.me/api/portraits/men/75.jpg"
    ),
    CallsScreenCallModel(
        id = 2,
        name = "Walt White",
        phone = "+4890856743",
        day = "03.12",
        time = "11:28",
        photoUrl = "https://randomuser.me/api/portraits/men/75.jpg"
    )
)
