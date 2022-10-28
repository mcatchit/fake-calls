package com.alenskaya.fakecalls.presentation.home

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.alenskaya.fakecalls.domain.contacts.model.FakeContact
import com.alenskaya.fakecalls.presentation.home.model.HomeScreenFakeContactModel
import com.alenskaya.fakecalls.presentation.home.ui.CreateCustomContactCell
import com.alenskaya.fakecalls.presentation.home.ui.FakeContactIconCell
import com.alenskaya.fakecalls.presentation.components.honeycomb.HoneyCombGrid
import com.alenskaya.fakecalls.presentation.home.ui.CreateNewCallTitle
import com.alenskaya.fakecalls.presentation.home.ui.LoadingProgress
import com.alenskaya.fakecalls.presentation.home.ui.MoreCell
import com.alenskaya.fakecalls.presentation.showToast
import dagger.hilt.android.migration.CustomInjection.inject

/**
 * Home screen ui
 */
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsState()
    val toastEvent by viewModel.oneTimeEffect.collectAsState(initial = null)

    val imageLoader = viewModel.imageLoader

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
        imageLoader = imageLoader,
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
    imageLoader: ImageLoader,
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

    val hasContacts = state.contacts.isNotEmpty()

    val gridElements = if (!hasContacts) staticCells else staticCells.addContactsCells(
        imageLoader = imageLoader,
        contacts = state.contacts,
        onSelectContactClick = onSelectContactClick,
        onChangeHint = onContactHintVisibilityChanged
    )

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.primary,
    ) {
        ConstraintLayout {
            val (title, progress, grid) = createRefs()

            CreateNewCallTitle(
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = 116.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            if (state.isLoading) {
                LoadingProgress(
                    modifier = Modifier
                        .constrainAs(progress) {
                            top.linkTo(title.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
            }

            HoneyCombGrid(
                width = screenWidth.value,
                padding = 30f,
                elements = gridElements,
                modifier = Modifier
                    .constrainAs(grid) {
                        top.linkTo(title.bottom, margin = 70.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
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
    imageLoader: ImageLoader,
    contacts: List<HomeScreenFakeContactModel>,
    onSelectContactClick: (FakeContact) -> Unit,
    onChangeHint: (Int, Boolean) -> Unit
) =
    mutableListOf<@Composable (BoxScope.() -> Unit)>().apply {
        add(this@addContactsCells.first())
        addAll(contacts.convertToFakeContactsCells(imageLoader, onSelectContactClick, onChangeHint))
        add(this@addContactsCells.last())
    }

private fun List<HomeScreenFakeContactModel>.convertToFakeContactsCells(
    imageLoader: ImageLoader,
    onSelectContactClick: (FakeContact) -> Unit,
    onChangeHint: (Int, Boolean) -> Unit
): List<@Composable BoxScope.() -> Unit> =
    map {
        {
            FakeContactIconCell(
                imageLoader = imageLoader,
                contactModel = it,
                onContactClick = onSelectContactClick,
                onChangeHint = onChangeHint
            )
        }
    }
