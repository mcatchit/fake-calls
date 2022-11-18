package com.alenskaya.fakecalls.presentation.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.alenskaya.fakecalls.R

/**
 * More item on home screen
 */
@Composable
fun MoreCell(onMoreClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primaryVariant)
            .clickable {
                onMoreClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "more",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSecondary,
        )
        Icon(
            contentDescription = "more",
            imageVector = ImageVector.vectorResource(id = R.drawable.more_icon),
            tint = MaterialTheme.colors.onSecondary
        )
    }
}
