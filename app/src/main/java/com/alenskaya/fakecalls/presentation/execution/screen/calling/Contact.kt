package com.alenskaya.fakecalls.presentation.execution.screen.calling

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.alenskaya.fakecalls.presentation.components.FakeContactIcon
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams

/**
 * Component for displaying static contact info.
 */
@Composable
fun Contact(
    imageLoader: ImageLoader,
    callExecutionParams: CallExecutionParams,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FakeContactIcon(
            url = callExecutionParams.photoUrl,
            size = 40.dp,
            description = callExecutionParams.name,
            imageLoader = imageLoader
        )
        Spacer(modifier = Modifier.height(20.dp))
        LargeTitle(text = callExecutionParams.name)
        Spacer(modifier = Modifier.height(12.dp))
        SmallTitle(text = callExecutionParams.phone)
    }
}
