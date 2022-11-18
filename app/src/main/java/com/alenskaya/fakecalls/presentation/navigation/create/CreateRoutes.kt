package com.alenskaya.fakecalls.presentation.navigation.create

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.alenskaya.fakecalls.presentation.features.create.CreateCallScreenMode
import com.alenskaya.fakecalls.presentation.navigation.NavigationDestination

/**
 * Create call screen routes
 */
object CreateRoutes {

    /**
     * Route to create call from fake contact
     */
    object CreateCallFromFakeContactRoute : CreateRoute {

        private const val PATH = "createFake"

        private const val FAKE_CONTACT_ID = "fakeContactId"

        override val route = "$PATH/{$FAKE_CONTACT_ID}"

        override val args: List<NamedNavArgument> =
            listOf(navArgument(FAKE_CONTACT_ID) { NavType.IntType })

        override fun getMode(backStackEntry: NavBackStackEntry): CreateCallScreenMode {
            return CreateCallScreenMode.CreateFake(backStackEntry.getInt(FAKE_CONTACT_ID))
        }

        /**
         * Creates destination with [fakeContactId]
         */
        fun createDestination(fakeContactId: Int) = object : NavigationDestination {
            override val destination = "$PATH/$fakeContactId"
        }
    }

    /**
     * Route to create call from custom contact
     */
    object CreateCallFromCustomContactRoute : CreateRoute {

        private const val PATH = "createCustom"

        override val route = PATH

        override val args: List<NamedNavArgument> = listOf()

        override fun getMode(backStackEntry: NavBackStackEntry): CreateCallScreenMode {
            return CreateCallScreenMode.CreateCustom
        }

        /**
         * Creates destination
         */
        fun createDestination() = object : NavigationDestination {
            override val destination = PATH
        }
    }

    /**
     * Route to edit the call
     */
    object EditCallRoute : CreateRoute {

        private const val PATH = "edit"

        private const val CALL_ID = "editableCallId"

        override val route = "$PATH/{$CALL_ID}"

        override val args: List<NamedNavArgument> =
            listOf(navArgument(CALL_ID) { NavType.IntType })

        override fun getMode(backStackEntry: NavBackStackEntry): CreateCallScreenMode {
            return CreateCallScreenMode.Edit(backStackEntry.getInt(CALL_ID))
        }

        /**
         * Creates destination with given [editableCallId]
         */
        fun createDestination(editableCallId: Int) = object : NavigationDestination {
            override val destination = "$PATH/$editableCallId"
        }
    }

    /**
     * Route to repeat a call
     */
    object RepeatCallRoute : CreateRoute {

        private const val PATH = "repeat"

        private const val CALL_ID = "repeatingCallId"

        override val route = "$PATH/{$CALL_ID}"

        override val args: List<NamedNavArgument> =
            listOf(navArgument(CALL_ID) { NavType.IntType })

        override fun getMode(backStackEntry: NavBackStackEntry): CreateCallScreenMode {
            return CreateCallScreenMode.Repeat(backStackEntry.getInt(CALL_ID))
        }

        /**
         * Creates destination with given [repeatingCallId]
         */
        fun createDestination(repeatingCallId: Int) = object : NavigationDestination {
            override val destination = "$PATH/$repeatingCallId"
        }
    }

    /**
     * List of all create call routes
     */
    val allRoutes = listOf(
        CreateCallFromFakeContactRoute,
        CreateCallFromCustomContactRoute,
        EditCallRoute,
        RepeatCallRoute
    )
}

private fun NavBackStackEntry.getInt(name: String): Int {
    return this.arguments?.getString(name)?.toInt() ?: error("argument cannot be null")
}

private fun NavBackStackEntry.getString(name: String): String {
    return this.arguments?.getString(name) ?: error("argument cannot be null")
}
