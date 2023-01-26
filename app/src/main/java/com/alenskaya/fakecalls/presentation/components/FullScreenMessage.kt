package com.alenskaya.fakecalls.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

/**
 * Displays fullscreen message.
 */
@Composable
fun FullScreenMessage(text: String) {
    PlaceInCenter {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3.copy(color = MaterialTheme.colors.secondaryVariant),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.Center)
        )
    }
}
