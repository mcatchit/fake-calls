package com.alenskaya.fakecalls.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import com.alenskaya.fakecalls.presentation.main.create.CreateCallScreenOneTimeUiEffect
import com.alenskaya.fakecalls.presentation.mvi.OneTimeUiEffect
import kotlinx.coroutines.flow.SharedFlow

/**
 * Shows toast with given [message]
 */
fun Context.showToast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}

/**
 * Subscribes composable to OneTimeUiEffect.
 */
@Composable
fun <T : OneTimeUiEffect> SharedFlow<T>.Subscribe(
    doWhenReceive: (T) -> Unit
) {
    LaunchedEffect(true) {
        collect { oneTimeUiEffect ->
            doWhenReceive(oneTimeUiEffect)
        }
    }
}

/**
 * Disables/enables gestures on children.
 * Stolen from
 * https://stackoverflow.com/questions/69142209/jetpack-compose-how-to-disable-gesture-detection-on-children
 */
fun Modifier.gesturesDisabled(disabled: Boolean = true) =
    if (disabled) {
        pointerInput(Unit) {
            awaitPointerEventScope {
                // we should wait for all new pointer events
                while (true) {
                    awaitPointerEvent(pass = PointerEventPass.Initial)
                        .changes
                        .forEach(PointerInputChange::consume)
                }
            }
        }
    } else {
        this
    }
