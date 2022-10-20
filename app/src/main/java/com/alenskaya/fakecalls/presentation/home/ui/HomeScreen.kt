package com.alenskaya.fakecalls.presentation.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.alenskaya.fakecalls.presentation.home.HomeScreenViewModel

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {
    val state by viewModel.state.collectAsState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Scaffold(backgroundColor = MaterialTheme.colors.primary) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CreateNewCallTitle(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 116.dp)
            )
            if (state.contacts.isNotEmpty()) {
                HoneyCombGrid(
                    width = screenWidth.value,
                    padding = 30f,
                    elements = state.contacts.map {
                        {
                            FakeContactIcon(contact = it, onContactClick = {})
                        }
                    },
                    modifier = Modifier
                        .padding(top = 70.dp)
                )
            }
        }

    }
}

@Composable
fun CreateNewCallTitle(modifier: Modifier) {
    Text(
        text = "Create new call",
        modifier = modifier,
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.onSecondary
    )
}
