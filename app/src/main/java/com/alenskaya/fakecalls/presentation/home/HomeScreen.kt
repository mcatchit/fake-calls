package com.alenskaya.fakecalls.presentation.home

import androidx.compose.foundation.layout.BoxScope
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
import com.alenskaya.fakecalls.domain.contacts.model.FakeContact
import com.alenskaya.fakecalls.presentation.home.ui.CreateCustomContactCell
import com.alenskaya.fakecalls.presentation.home.ui.FakeContactIconCell
import com.alenskaya.fakecalls.presentation.home.ui.HoneyCombGrid
import com.alenskaya.fakecalls.presentation.home.ui.LoadingProgress
import com.alenskaya.fakecalls.presentation.home.ui.MoreCell

//TODO Refactoring!
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {
    val state by viewModel.screenState.collectAsState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val staticItems = getStaticItems(
        onCreateCustomClick = {},
        onMoreClick = {}
    )

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
            if (state.isLoading) {
                LoadingProgress(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )
            }

            val gridElements = if (state.isLoading) staticItems else staticItems.addLoadedContacts(
                state.contacts,
                onSelectContactClick = {}
            )

            HoneyCombGrid(
                width = screenWidth.value,
                padding = 30f,
                elements = gridElements,
                modifier = Modifier
                    .padding(top = 70.dp),
                maxElementsInRow = if (state.isLoading) 2 else 3,
                startWithBig = state.isLoading
            )
        }
    }
}

private fun getStaticItems(
    onCreateCustomClick: () -> Unit,
    onMoreClick: () -> Unit
): List<@Composable BoxScope.() -> Unit> {
    return mutableListOf<@Composable BoxScope.() -> Unit>().apply {
        add {
            CreateCustomContactCell(
                onCreateClick = onCreateCustomClick
            )
        }
        add {
            MoreCell(
                onMoreClick = onMoreClick
            )
        }
    }
}

private fun List<@Composable BoxScope.() -> Unit>.addLoadedContacts(
    contacts: List<FakeContact>,
    onSelectContactClick: (FakeContact) -> Unit,
) =
    mutableListOf<@Composable (BoxScope.() -> Unit)>().apply {
        add(this@addLoadedContacts[0])
        addAll(convertToFakeContactsCells(contacts, onSelectContactClick))
        add(this@addLoadedContacts[1])
    }

private fun convertToFakeContactsCells(
    contacts: List<FakeContact>,
    onSelectContactClick: (FakeContact) -> Unit
): List<@Composable BoxScope.() -> Unit> =
    contacts.map {
        {
            FakeContactIconCell(
                contact = it,
                onContactClick = onSelectContactClick
            )
        }
    }

@Composable
fun CreateNewCallTitle(modifier: Modifier = Modifier) {
    Text(
        text = "Create new call",
        modifier = modifier,
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.onSecondary
    )
}