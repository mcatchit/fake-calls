package com.alenskaya.fakecalls.presentation.main.phonebook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.alenskaya.fakecalls.presentation.components.BackIconHeader
import com.alenskaya.fakecalls.presentation.components.FullScreenLoadingMessage
import com.alenskaya.fakecalls.presentation.components.FullScreenMessage
import com.alenskaya.fakecalls.presentation.main.phonebook.ui.PhonebookList
import com.alenskaya.fakecalls.presentation.phonebook.PhonebookContact

@Composable
fun PhonebookScreen(
    viewModel: PhonebookScreenViewModel = hiltViewModel()
) {
    val state by viewModel.screenState.collectAsState()

    val imageLoader = viewModel.imageLoader
    val strings = viewModel.phonebookStrings

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        color = MaterialTheme.colors.background,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            BackIconHeader(strings.navigateBack(), strings.title()) {
                viewModel.sendEvent(PhonebookScreenEvent.NavigateBack)
            }
            PhonebookScreen(
                state = state,
                strings = strings,
                imageLoader = imageLoader,
                onContactSelectedCallback = { contact ->
                    viewModel.sendEvent(PhonebookScreenEvent.SelectContact(contact))
                }
            )
        }
    }
}

@Composable
private fun PhonebookScreen(
    state: PhonebookScreenState,
    strings: PhonebookStrings,
    imageLoader: ImageLoader,
    onContactSelectedCallback: (PhonebookContact) -> Unit
) {
    when {
        state.isLoading -> FullScreenLoadingMessage(text = strings.loading())
        state.errorMessage != null -> FullScreenMessage(text = state.errorMessage)
        else -> PhonebookList(
            contacts = state.contacts,
            imageLoader = imageLoader,
            onContactSelectedCallback = onContactSelectedCallback
        )
    }
}
