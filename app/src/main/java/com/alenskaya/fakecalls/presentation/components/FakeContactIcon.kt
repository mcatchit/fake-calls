package com.alenskaya.fakecalls.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.components.honeycomb.HoneyCombCell
import com.alenskaya.fakecalls.presentation.components.honeycomb.hexagonalSize

/**
 * Component for displaying contact icon.
 * @param url - photo url, if null default icon is used.
 * @param size - size of icon in dp.
 * @param description - image description (for accessibility).
 * @param imageLoader - application image loader.
 * @param modifier - modifier
 */
@Composable
fun FakeContactIcon(
    url: String?,
    size: Dp,
    description: String,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    val iconSize = hexagonalSize(with(LocalDensity.current) { size.toPx() })

    HoneyCombCell(
        height = iconSize.height,
        width = iconSize.width,
        modifier = modifier
    ) {
        if (url != null) {
            UrlContactIcon(
                url = url,
                description = description,
                imageLoader = imageLoader
            )
        } else {
            DefaultContactIcon()
        }
    }
}

@Composable
private fun UrlContactIcon(
    url: String,
    description: String,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        imageLoader = imageLoader,
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
