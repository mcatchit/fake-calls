package com.alenskaya.fakecalls.presentation.calls.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.presentation.components.DashedShape
import com.alenskaya.fakecalls.presentation.components.honeycomb.HoneyCombCell
import com.alenskaya.fakecalls.presentation.components.honeycomb.hexagonalSize

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
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (icon, contactInfo, dateInfo, buttons, divider) = createRefs()

        FakeContactIcon(
            url = call.photoUrl,
            description = call.name,
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
                    horizontalChainWeight = 0.7f
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
                        startMargin = 10.dp,
                        endMargin = 6.dp,
                        bias = 0f
                    )
                    width = Dimension.fillToConstraints
                    horizontalChainWeight = 0.3f
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

        CallDivider(
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
private fun FakeContactIcon(
    url: String?,
    description: String,
    modifier: Modifier = Modifier
) {
    val iconSize = hexagonalSize(with(LocalDensity.current) { 24.dp.toPx() })

    HoneyCombCell(
        height = iconSize.height,
        width = iconSize.width,
        modifier = modifier
    ) {
        if (url != null) {
            UrlContactIcon(
                url = url,
                description = description,
            )
        } else {
            DefaultContactIcon()
        }
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
    Column(modifier = modifier) {
        SmallText(text = day)
        Spacer(modifier = Modifier.height(6.dp))
        SmallText(text = time)
    }
}

@Composable
private fun CallDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                shape = DashedShape(12.dp),
                color = MaterialTheme.colors.primaryVariant
            )
    )
}

@Composable
private fun UrlContactIcon(
    url: String,
    description: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        //imageLoader = imageLoader,
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = description,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
    )
}

@Composable
private fun DefaultContactIcon(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.default_avatar),
        contentScale = ContentScale.Crop,
        contentDescription = "Avatar icon is absent", //FIXME
        modifier = modifier
            .fillMaxSize()
    )
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
