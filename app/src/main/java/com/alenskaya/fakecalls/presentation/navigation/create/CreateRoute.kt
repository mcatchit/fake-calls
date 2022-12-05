package com.alenskaya.fakecalls.presentation.navigation.create

import androidx.navigation.NavBackStackEntry
import com.alenskaya.fakecalls.presentation.main.create.CreateCallScreenMode
import com.alenskaya.fakecalls.presentation.navigation.Route

/**
 * Basic interface for create call screen navigation routes
 */
interface CreateRoute : Route {

    /**
     * Extracts create call screen mode from arguments of [backStackEntry]
     */
    fun getMode(backStackEntry: NavBackStackEntry): CreateCallScreenMode
}
