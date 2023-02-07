package com.alenskaya.fakecalls.presentation.execution

import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri

const val CALL_NOTIFICATION_TIMEOUT = 20000L

const val CHANNEL_ID = "calls_notification_channel"

val CALL_SOUND: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)

val CALL_AUDIO_ATTRIBUTES: AudioAttributes = AudioAttributes.Builder()
    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
    .build()
