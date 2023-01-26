package com.alenskaya.fakecalls.presentation.main.phonebook.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.alenskaya.fakecalls.presentation.components.DashedDivider
import com.alenskaya.fakecalls.presentation.components.FakeContactIcon
import com.alenskaya.fakecalls.presentation.phonebook.PhonebookContact

/**
 * Displays information about one phonebook contact.
 */
@Composable
fun PhonebookContactRow(
    contact: PhonebookContact,
    imageLoader: ImageLoader,
    doOnClickCallback: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    onClick = doOnClickCallback,
                    indication = null
                )
        ) {
            FakeContactIcon(
                uri = contact.photoUri,
                size = 16.dp,
                description = contact.name,
                imageLoader = imageLoader
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                BigText(text = contact.name)
                Spacer(modifier = Modifier.height(6.dp))
                SmallText(text = contact.phone)
            }
        }
        DashedDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
    }
}

@Composable
private fun BigText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onSecondary),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun SmallText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle2.copy(color = MaterialTheme.colors.secondary),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
