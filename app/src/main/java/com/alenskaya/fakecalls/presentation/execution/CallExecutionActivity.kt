package com.alenskaya.fakecalls.presentation.execution

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.alenskaya.fakecalls.presentation.execution.screen.CallExecutionScreen
import com.alenskaya.fakecalls.presentation.theme.FakeCallsTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity for executing a call.
 *
 * Calls are not executed in main activity in order to separate this process from main flow
 * and make all return operations close app instead of returning to main flow.
 * Also separate activity simplifies processing bundles, enabling displaying on locked screen, etc.
 */
@AndroidEntryPoint
class CallExecutionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showOnLockScreenAndTurnScreenOn()

        val bundle = intent.extras ?: error("Call execution screen bundle cannot be null")

        setContent {
            FakeCallsTheme {
                CallExecutionScreen(
                    callExecutionParams = bundle.extractCallExecutionParams(),
                    exitApplicationAction = ::exitApplicationAction
                )
            }
        }
    }

    private fun showOnLockScreenAndTurnScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        exitApplicationAction()
    }

    private fun exitApplicationAction() {
        finish()
        moveTaskToBack(true)
    }
}
