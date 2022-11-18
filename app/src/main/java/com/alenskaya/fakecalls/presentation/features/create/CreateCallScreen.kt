package com.alenskaya.fakecalls.presentation.features.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CreateCallScreen(mode: CreateCallScreenMode) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
    ) {
        if (mode is CreateCallScreenMode.CreateFake) {
            Text(text = mode.id.toString())
        }
    }
}
