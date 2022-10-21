package com.alenskaya.fakecalls.presentation.home

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alenskaya.fakecalls.domain.contacts.model.FakeContact
import com.alenskaya.fakecalls.presentation.home.model.HomeScreenFakeContactModel
import com.alenskaya.fakecalls.presentation.home.ui.CreateCustomContactCell
import com.alenskaya.fakecalls.presentation.home.ui.FakeContactIconCell
import com.alenskaya.fakecalls.presentation.components.honeycomb.HoneyCombGrid
import com.alenskaya.fakecalls.presentation.home.ui.CreateNewCallTitle
import com.alenskaya.fakecalls.presentation.home.ui.LoadingProgress
import com.alenskaya.fakecalls.presentation.home.ui.MoreCell
import com.alenskaya.fakecalls.presentation.showToast

/**
 * Home screen ui
 */
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {
    val state by viewModel.screenState.collectAsState()
    val toastEvent by viewModel.oneTimeEffect.collectAsState(initial = null)

    val onSelectContactClick = { _: FakeContact ->

    }

    val onContactHintVisibilityChanged = { id: Int, isHinted: Boolean ->
        viewModel.contactHintVisibilityChanged(id, isHinted)
    }

    val onCreateCustomClick = {

    }

    val onMoreClick = {

    }

    HomeScreen(
        state = state,
        onSelectContactClick = onSelectContactClick,
        onContactHintVisibilityChanged = onContactHintVisibilityChanged,
        onCreateCustomClick = onCreateCustomClick,
        onMoreClick = onMoreClick
    )

    toastEvent?.let { homeScreenOneTimeUiEffect ->
        LocalContext.current.showToast(homeScreenOneTimeUiEffect.toastMessage)
    }
}

@Composable
private fun HomeScreen(
    state: HomeScreenState,
    onSelectContactClick: (FakeContact) -> Unit,
    onContactHintVisibilityChanged: (Int, Boolean) -> Unit,
    onCreateCustomClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val staticCells = getStaticCells(
        onCreateCustomClick = onCreateCustomClick,
        onMoreClick = onMoreClick
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

            val hasContacts = state.contacts.isNotEmpty()

            val gridElements = if (!hasContacts) staticCells else staticCells.addContactsCells(
                contacts = state.contacts,
                onSelectContactClick = onSelectContactClick,
                onChangeHint = onContactHintVisibilityChanged
            )

            HoneyCombGrid(
                width = screenWidth.value,
                padding = 30f,
                elements = gridElements,
                modifier = Modifier
                    .padding(top = 70.dp),
                maxElementsInRow = if (hasContacts) 3 else 2,
                startWithBigLine = !hasContacts
            )
        }
    }
}

private fun getStaticCells(
    onCreateCustomClick: () -> Unit,
    onMoreClick: () -> Unit
): List<@Composable BoxScope.() -> Unit> = mutableListOf<@Composable BoxScope.() -> Unit>().apply {
    add {
        CreateCustomContactCell(onCreateClick = onCreateCustomClick)
    }
    add {
        MoreCell(onMoreClick = onMoreClick)
    }
}

private fun List<@Composable BoxScope.() -> Unit>.addContactsCells(
    contacts: List<HomeScreenFakeContactModel>,
    onSelectContactClick: (FakeContact) -> Unit,
    onChangeHint: (Int, Boolean) -> Unit
) =
    mutableListOf<@Composable (BoxScope.() -> Unit)>().apply {
        add(this@addContactsCells.first())
        addAll(contacts.convertToFakeContactsCells(onSelectContactClick, onChangeHint))
        add(this@addContactsCells.last())
    }

private fun List<HomeScreenFakeContactModel>.convertToFakeContactsCells(
    onSelectContactClick: (FakeContact) -> Unit,
    onChangeHint: (Int, Boolean) -> Unit
): List<@Composable BoxScope.() -> Unit> =
    map {
        {
            FakeContactIconCell(
                contactModel = it,
                onContactClick = onSelectContactClick,
                onChangeHint = onChangeHint
            )
        }
    }
