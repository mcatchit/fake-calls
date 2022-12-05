package com.alenskaya.fakecalls.presentation.execution.screen

import androidx.lifecycle.ViewModel
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * View model of call execution screen.
 * TODO add processing of accepting a call
 */
@HiltViewModel
class CallExecutionScreenViewModel @Inject constructor(
    val imageLoader: ImageLoader
) : ViewModel() {
}