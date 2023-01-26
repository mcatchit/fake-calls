package com.alenskaya.fakecalls.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.alenskaya.fakecalls.R

@Composable
fun BackIconHeader(
    description: String,
    title: String? = null,
    doOnBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            contentDescription = description,
            imageVector = ImageVector.vectorResource(id = R.drawable.back_icon),
            modifier = Modifier.clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                doOnBackClick()
            }
        )
        title?.let {
            Spacer(modifier = Modifier.width(16.dp))
            MainTitle(text = title, style = MaterialTheme.typography.h3)
        }
    }
}