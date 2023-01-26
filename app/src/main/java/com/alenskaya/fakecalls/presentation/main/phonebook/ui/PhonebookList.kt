package com.alenskaya.fakecalls.presentation.main.phonebook.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.LocalImageLoader
import com.alenskaya.fakecalls.presentation.phonebook.PhonebookContact
import com.alenskaya.fakecalls.presentation.theme.FakeCallsTheme

/**
 * Displays list of phonebook contacts.
 */
@Composable
fun PhonebookList(
    contacts: List<PhonebookContact>,
    imageLoader: ImageLoader,
    onContactSelectedCallback: (PhonebookContact) -> Unit
) {
    LazyColumn {
        items(contacts) { contact ->
            PhonebookContactRow(contact, imageLoader) {
                onContactSelectedCallback(contact)
            }
        }
    }
}

@Preview
@Composable
private fun ListOfContactsPreview() {
    FakeCallsTheme {
        val contacts = listOf(
            PhonebookContact("1", "James Brown", "+37678128", null),
            PhonebookContact("2", "Braulia Sansa", "+453635368", null),
            PhonebookContact("3", "Kris Hamswort", "+376700923", null),
        )
        PhonebookList(
            contacts = contacts,
            imageLoader = LocalImageLoader.current,
            onContactSelectedCallback = {}
        )
    }
}
