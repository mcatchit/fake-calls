package com.alenskaya.fakecalls.presentation.calls

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alenskaya.fakecalls.R

/**
 * Component which animates its content expansion.
 * Consists of static title and clickable animated arrow which launches expansion/collapsing of main content.
 * @param title - section title.
 * @param modifier - modifier.
 * @param content - main content.
 */
@Composable
fun ExpandableSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var isOpened by remember { mutableStateOf(true) }

    val rotationState by animateFloatAsState(
        targetValue = if (isOpened) 0f else 180f,
    )

    Column(
        modifier = modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleText(
                text = title,
                modifier = Modifier.weight(1f)
            )

            ArrowButton(rotationState = rotationState) {
                isOpened = !isOpened
            }

        }
        if (isOpened) {
            content.invoke()
        }
    }
}

@Composable
private fun TitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h3.copy(color = MaterialTheme.colors.onSecondary),
        modifier = modifier
    )
}

@Composable
private fun ArrowButton(rotationState: Float, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .alpha(ContentAlpha.medium)
            .rotate(rotationState),
        interactionSource = MutableInteractionSource()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.arrow_icon),
            contentDescription = "Drop-Down Arrow" //FIXME
        )
    }
}

@Composable
@Preview
fun ExpandableSectionPreview() {
    Column {
        ExpandableSection(
            title = "Section",
            modifier = Modifier
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(color = Color.Red)
            )
        }
    }
}
