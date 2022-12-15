package com.alenskaya.fakecalls.presentation

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Provides access to application string resources.
 */
interface AppStrings {

    /**
     * Returns String with specified [id]
     */
    fun getString(id: Int): String
}

class AppStringsImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppStrings {

    override fun getString(id: Int): String {
        return context.getString(id)
    }
}
