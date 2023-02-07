package com.alenskaya.fakecalls.presentation.execution

import android.app.KeyguardManager
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager

/**
 * Plays call ringtone if application is not on lockscreen.
 */
class CallRingtonePlayer(
    private val context: Context
) {

    private var ringtone: Ringtone? = null

    /**
     * Starts ringtone.
     */
    fun start() {
        if (!isOnLockScreen()) {
            ringtone = RingtoneManager.getRingtone(context, CALL_SOUND).apply {
                play()
            }
        }
    }

    /**
     * Stops ringtone.
     */
    fun stop() {
        ringtone?.stop()
    }

    private fun isOnLockScreen() =
        (context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager).isKeyguardLocked
}