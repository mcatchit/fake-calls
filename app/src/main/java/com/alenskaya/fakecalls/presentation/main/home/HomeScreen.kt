package com.alenskaya.fakecalls.presentation.main.home

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
import com.alenskaya.fakecalls.presentation.Subscribe
import com.alenskaya.fakecalls.presentation.main.home.model.HomeScreenFakeContactModel
import com.alenskaya.fakecalls.presentation.main.home.ui.CreateCustomContactCell
import com.alenskaya.fakecalls.presentation.main.home.ui.FakeContactIconCell
import com.alenskaya.fakecalls.presentation.components.honeycomb.HoneyCombGrid
import com.alenskaya.fakecalls.presentation.components.MainTitle
import com.alenskaya.fakecalls.presentation.components.LoadingProgress
import com.alenskaya.fakecalls.presentation.main.home.ui.MoreCell
import com.alenskaya.fakecalls.presentation.showToast

/**
 * Home screen ui.
 */
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsState()

    val imageLoader = viewModel.imageLoader

    HomeScreen(
        imageLoader = imageLoader,
        homeStrings = viewModel.homeStrings,
        state = state,
        onSelectContactClick = { id ->
            viewModel.sendEvent(HomeScreenEvent.CreateCallFromSuggested(id))
        },
        onContactHintVisibilityChanged = { id: Int, isHinted: Boolean ->
            viewModel.sendEvent(HomeScreenEvent.ContactHintVisibilityChanged(id, isHinted))
        },
        onCreateCustomClick = {
            viewModel.sendEvent(HomeScreenEvent.CreateCustomCall)
        },
        onMoreClick = {}
    )

    val context = LocalContext.current

    viewModel.oneTimeEffect.Subscribe { homeScreenOneTimeUiEffect ->
        context.showToast(homeScreenOneTimeUiEffect.toastMessage)
    }
}

@Composable
private fun HomeScreen(
    imageLoader: ImageLoader,
    homeStrings: HomeStrings,
    state: HomeScreenState,
    onSelectContactClick: (Int) -> Unit,
    onContactHintVisibilityChanged: (Int, Boolean) -> Unit,
    onCreateCustomClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val staticCells = getStaticCells(
        customText = homeStrings.createCustomButton(),
        onCreateCustomClick = onCreateCustomClick,
        moreText = homeStrings.moreButton(),
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

            MainTitle(
                text = homeStrings.title(),
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = 116.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            if (state.isLoading) {
                LoadingProgress(
                    text = homeStrings.loadingSuggestions(),
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
    customText: String,
    onCreateCustomClick: () -> Unit,
    moreText: String,
    onMoreClick: () -> Unit
): List<@Composable BoxScope.() -> Unit> = mutableListOf<@Composable BoxScope.() -> Unit>().apply {
    add {
        CreateCustomContactCell(
            text = customText,
            onCreateClick = onCreateCustomClick
        )
    }
    add {
        MoreCell(
            text = moreText,
            onMoreClick = onMoreClick
        )
    }
}

private fun List<@Composable BoxScope.() -> Unit>.addContactsCells(
    imageLoader: ImageLoader,
    contacts: List<HomeScreenFakeContactModel>,
    onSelectContactClick: (Int) -> Unit,
    onChangeHint: (Int, Boolean) -> Unit
) =
    mutableListOf<@Composable (BoxScope.() -> Unit)>().apply {
        add(this@addContactsCells.first())
        addAll(contacts.convertToFakeContactsCells(imageLoader, onSelectContactClick, onChangeHint))
        add(this@addContactsCells.last())
    }

private fun List<HomeScreenFakeContactModel>.convertToFakeContactsCells(
    imageLoader: ImageLoader,
    onSelectContactClick: (Int) -> Unit,
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
