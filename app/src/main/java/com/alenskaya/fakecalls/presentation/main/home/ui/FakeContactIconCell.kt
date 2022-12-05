package com.alenskaya.fakecalls.presentation.main.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alenskaya.fakecalls.presentation.main.home.model.HomeScreenFakeContactModel

/**
 * Displays fake contact icon and shows hint on long click
 * @param imageLoader - application image loader
 * @param contactModel - contact information
 * @param onContactClick - action called by contact click
 * @param onChangeHint - action called when hint visibility changes
 */
@Composable
fun FakeContactIconCell(
    imageLoader: ImageLoader,
    contactModel: HomeScreenFakeContactModel,
    onContactClick: (Int) -> Unit,
    onChangeHint: (Int, Boolean) -> Unit
) {
    FakeContactImage(
        imageLoader = imageLoader,
        contact = contactModel,
        onContactClick = onContactClick,
        showHint = { onChangeHint(contactModel.id, true) }
    )

    if (contactModel.isHintVisible) {
        FakeContactHint(
            contact = contactModel,
            onContactClick = onContactClick,
            hideHint = { onChangeHint(contactModel.id, false) }
        )
    }
}

@Composable
private fun FakeContactImage(
    imageLoader: ImageLoader,
    contact: HomeScreenFakeContactModel,
    onContactClick: (Int) -> Unit,
    showHint: () -> Unit
) {
    AsyncImage(
        imageLoader = imageLoader,
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
                    onTap = { onContactClick(contact.id) }
                )
            }
    )
}

@Composable
private fun FakeContactHint(
    contact: HomeScreenFakeContactModel,
    onContactClick: (Int) -> Unit,
    hideHint: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.onSecondary.copy(alpha = 0.8f))
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { hideHint() },
                    onTap = { onContactClick(contact.id) }
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HintText(text = contact.name)
        HintText(text = contact.phone)
        HintText(text = contact.country)
    }
}

@Composable
fun HintText(text: String) {
    Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = Color.White,
        fontSize = 12.sp,
        modifier = Modifier.padding(
            horizontal = 12.dp
        )
    )
}
