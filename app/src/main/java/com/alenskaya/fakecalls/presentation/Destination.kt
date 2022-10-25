package com.alenskaya.fakecalls.presentation

import com.alenskaya.fakecalls.R

/**
 * Interface for main screen destinations
 */
interface Destination {
    val route: String
    val iconPath: Int
}

object Home : Destination {
    override val route = "home"
    override val iconPath = R.drawable.home_icon
}

object Calls : Destination {
    override val route = "calls"
    override val iconPath = R.drawable.calls_icon
}
