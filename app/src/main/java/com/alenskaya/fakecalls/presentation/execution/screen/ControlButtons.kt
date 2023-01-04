package com.alenskaya.fakecalls.presentation.execution.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alenskaya.fakecalls.presentation.theme.FakeCallsTheme

@Composable
fun ControlButtons(
    doOnAccept: () -> Unit,
    doOnDecline: () -> Unit
) {

}

@Preview
@Composable
private fun ControlButtonsPreview() {
    FakeCallsTheme {
        ControlButtons({}, {})
    }
}






