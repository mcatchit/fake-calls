package com.alenskaya.fakecalls.presentation.features.execution

import androidx.lifecycle.ViewModel
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CallExecutionScreenViewModel @Inject constructor(
    val imageLoader: ImageLoader
) : ViewModel() {
}