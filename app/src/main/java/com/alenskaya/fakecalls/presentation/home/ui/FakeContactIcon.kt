package com.alenskaya.fakecalls.presentation.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alenskaya.fakecalls.domain.contacts.model.FakeContact

/**
 * Displays fake contact icon and shows hint on long click
 */
@Composable
fun FakeContactIcon(
    contact: FakeContact,
    onContactClick: (FakeContact) -> Unit
) {
    var showHint by remember {
        mutableStateOf(false)
    }

    FakeContactImage(
        contact = contact,
        onContactClick = onContactClick,
        showHint = { showHint = true }
    )

    if (showHint) {
        FakeIconHint(
            contact = contact,
            onContactClick = onContactClick,
            hideHint = { showHint = false }
        )
    }
}

@Composable
private fun FakeContactImage(
    contact: FakeContact,
    onContactClick: (FakeContact) -> Unit,
    showHint: () -> Unit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(contact.photoUrl)
            .crossfade(true)
            .build(),
        contentDescription = contact.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { showHint() },
                    onTap = { onContactClick(contact) }
                )
            }
    )
}

@Composable
private fun FakeIconHint(
    contact: FakeContact,
    onContactClick: (FakeContact) -> Unit,
    hideHint: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.onSecondary.copy(alpha = 0.7f))
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { hideHint() },
                    onTap = { onContactClick(contact) }
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HintText(text = contact.name)
        HintText(text = contact.phone)
    }
}

@Composable
fun HintText(text: String) {
    Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = Color.White,
        fontSize = 12.sp
    )
}