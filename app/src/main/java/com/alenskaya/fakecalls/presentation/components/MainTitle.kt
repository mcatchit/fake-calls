package com.alenskaya.fakecalls.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

/**
 * Home screen title.
 */
@Composable
fun MainTitle(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.h1
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = MaterialTheme.colors.onSecondary
    )
}
