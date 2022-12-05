package com.alenskaya.fakecalls.presentation.main.home

import com.alenskaya.fakecalls.presentation.mvi.OneTimeUiEffect

/**
 * At the moment Home Screen has only one one-time effect - displaying toast message
 */
data class HomeScreenOneTimeUiEffect(val toastMessage: String) : OneTimeUiEffect
