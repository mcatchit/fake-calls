package com.alenskaya.fakecalls.presentation.main.calls.ui.row

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.ImageLoader
import com.alenskaya.fakecalls.presentation.components.DashedDivider
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.presentation.components.FakeContactIcon

/**
 * Base call information row.
 * @param call - information about the call.
 * @param actions - list of actions.
 * @param modifier - modifier.
 */
@Composable
fun CallRow(
    call: CallsScreenCallModel,
    actions: List<CallRowAction>,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (icon, contactInfo, dateInfo, buttons, divider) = createRefs()

        FakeContactIcon(
            uri = call.photoUrl,
            size = 18.dp,
            description = call.name,
            imageLoader = imageLoader,
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top, margin = 2.dp)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom, margin = 12.dp)
                }
        )

        ContactInfo(
            name = call.name,
            phone = call.phone,
            modifier = Modifier
                .constrainAs(contactInfo) {
                    top.linkTo(parent.top, margin = 16.dp)
                    linkTo(icon.end, dateInfo.start, startMargin = 10.dp, bias = 0f)
                    width = Dimension.fillToConstraints
                    horizontalChainWeight = 0.65f
                }
        )

        DateInfo(
            day = call.day,
            time = call.time,
            modifier = Modifier
                .constrainAs(dateInfo) {
                    top.linkTo(parent.top, margin = 16.dp)
                    linkTo(
                        contactInfo.end,
                        buttons.start,
                        startMargin = 6.dp,
                        endMargin = 12.dp,
                        bias = 0f
                    )
                    width = Dimension.fillToConstraints
                    horizontalChainWeight = 0.35f
                }
        )

        CallRowActionsButtons(
            actions = actions,
            modifier = Modifier
                .constrainAs(buttons) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom, margin = 6.dp)
                    end.linkTo(parent.end)
                }
        )

        DashedDivider(
            modifier = Modifier
                .constrainAs(divider) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
private fun ContactInfo(name: String, phone: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        BigText(text = name)
        Spacer(modifier = Modifier.height(6.dp))
        SmallText(text = phone)
    }
}

@Composable
private fun DateInfo(day: String, time: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        SmallText(text = day)
        Spacer(modifier = Modifier.height(6.dp))
        SmallText(text = time)
    }
}

@Composable
private fun BigText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onSecondary),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun SmallText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle2.copy(color = MaterialTheme.colors.secondary),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
