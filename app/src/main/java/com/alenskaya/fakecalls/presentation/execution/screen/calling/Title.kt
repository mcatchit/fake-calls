package com.alenskaya.fakecalls.presentation.execution.screen.calling

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun SmallTitle(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onPrimary
) {
    Title(
        text = text,
        style = MaterialTheme.typography.subtitle2,
        color = color,
        modifier = modifier
    )
}

@Composable
fun LargeTitle(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onPrimary
) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle1,
        color = color,
        modifier = modifier
    )
}

@Composable
private fun Title(
    text: String,
    color: Color = MaterialTheme.colors.onPrimary,
    style: TextStyle,
    modifier: Modifier
) {
    Text(
        text = text,
        style = style,
        color = color,
        modifier = modifier
    )
}
