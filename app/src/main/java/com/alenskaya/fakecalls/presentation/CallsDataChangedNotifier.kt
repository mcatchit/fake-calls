package com.alenskaya.fakecalls.presentation

/**
 * Service which notifies listeners that calls data has changed.
 */
class CallsDataChangedNotifier {

    private val listeners = mutableListOf<CallsDataChangedListener>()

    /**
     * Subscribes [listener] to call data changes.
     */
    fun addListener(listener: CallsDataChangedListener) {
        listeners.add(listener)
    }

    /**
     * Unsubscribes [listener] from call data updates.
     */
    fun removeListener(listener: CallsDataChangedListener) {
        listeners.remove(listener)
    }

    /**
     * Execute notification of all listeners that calls data has changed.
     */
    fun callsDataChanged() {
        listeners.forEach { listener ->
            listener.callsDataChanged()
        }
    }
}

/**
 * Listener of call data updates.
 * In order to receive notifications from [CallsDataChangedNotifier], should be subscribed to it.
 * In the end of its lifecycle should be unsubscribed.
 */
interface CallsDataChangedListener {
    fun callsDataChanged()
}
