package com.alenskaya.fakecalls.presentation.execution.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.LocalImageLoader
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.components.FakeContactIcon
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.theme.FakeCallsTheme

/**
 * Screen shown when call is executing but not accepted or declined yet.
 */
@Composable
fun CallingScreen(
    imageLoader: ImageLoader,
    callExecutionParams: CallExecutionParams,
    doOnAccept: () -> Unit,
    doOnDecline: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primaryVariant)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Incoming call",//FIXME
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(40.dp))
            FakeContactIcon(
                url = callExecutionParams.photoUrl,
                size = 60.dp,
                description = "photo", //FIXME
                imageLoader = imageLoader
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = callExecutionParams.name,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = callExecutionParams.phone,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(60.dp))
            AcceptDeclineButtons(
                doOnAccept = doOnAccept,
                doOnDecline = doOnDecline
            )
        }
    }
}

@Composable
private fun AcceptDeclineButtons(
    doOnAccept: () -> Unit,
    doOnDecline: () -> Unit
) {
    Row {
        AcceptButton(
            image = R.drawable.incoming_call_icon,
            description = "Accept", //FIXME
            doOnClickAction = doOnAccept
        )

        Spacer(modifier = Modifier.width(60.dp))

        AcceptButton(
            image = R.drawable.decline_call_icon,
            description = "Decline", //FIXME
            doOnClickAction = doOnDecline
        )
    }
}

@Composable
private fun AcceptButton(
    image: Int,
    description: String,
    doOnClickAction: () -> Unit
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = description,
        modifier = Modifier
            .size(100.dp)
            .clickable {
                doOnClickAction()
            }
    )
}

@Preview
@Composable
private fun CallingPreview() {
    FakeCallsTheme {
        CallingScreen(
            imageLoader = LocalImageLoader.current,
            callExecutionParams = CallExecutionParams(
                1,
                "Kendall Stewart",
                "+342342334",
                "https://tinypng.com/images/social/website.jpg"
            ),
            doOnAccept = {},
            doOnDecline = {}
        )
    }
}
