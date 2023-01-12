package com.alenskaya.fakecalls.presentation.execution.screen.ongoing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Timer of call execution.
 * Every second calls callback [updateTimeCallback] and passes value of passed seconds.
 * @param coroutineScope - scope where timer should be launched.
 * @param timeLimitExceededCallBack - called when time limit exceeded.
 */
class OngoingCallSecondsTimer(
    private val coroutineScope: CoroutineScope,
    private val updateTimeCallback: (Int) -> Unit,
    private val timeLimitExceededCallBack: () -> Unit
) {
    private var timerJob: Job? = null

    private var isRunning = true
    private var seconds = 0

    /**
     * Starts timer.
     */
    fun start() {
        timerJob = coroutineScope.launch(Dispatchers.Main) {
            updateTimeCallback(seconds)

            while (isRunning) {
                delay(ONE_SECOND_DELAY)

                seconds++

                if (seconds < ONE_HOUR_IN_SECONDS) {
                    updateTimeCallback(seconds)
                } else {
                    timeLimitExceededCallBack()
                    stop()
                }
            }
        }
    }

    /**
     * Stops timer.
     */
    fun stop() {
        isRunning = false
        timerJob?.cancel()
    }

    private companion object {
        private const val ONE_SECOND_DELAY = 1000L
        private const val ONE_HOUR_IN_SECONDS = 3600
    }
}
