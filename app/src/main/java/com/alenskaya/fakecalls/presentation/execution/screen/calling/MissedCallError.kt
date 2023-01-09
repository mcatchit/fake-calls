package com.alenskaya.fakecalls.presentation.execution.screen.calling

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alenskaya.fakecalls.R

/**
 * Displays error that the call is missed/declined.
 */
@Composable
fun MissedCallError(
    text: String,
    isVisible: Boolean,
    modifier: Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallTitle(text = text, color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.missed_call_icon),
                contentDescription = text,
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}
