package com.alenskaya.fakecalls.presentation.execution.screen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ControlButton(
    image: Int,
    description: String,
    modifier: Modifier = Modifier,
    tintColor: Color = Color.Transparent
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = description,
        colorFilter = ColorFilter.tint(tintColor, blendMode = BlendMode.Color),
        modifier = modifier
            .clip(CircleShape)
            .size(60.dp)
    )
}
