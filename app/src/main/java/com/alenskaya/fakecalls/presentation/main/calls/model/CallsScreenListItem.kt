package com.alenskaya.fakecalls.presentation.main.calls.model

/**
 * Items of calls list.
 */
sealed class CallsScreenListItem {

    /**
     * Expandable calls group header.
     */
    data class Header(

        /**
         * Group title.
         */
        val title: String,

        /**
         * Is group opened.
         */
        val isOpened: Boolean,

        /**
         * Type of calls represented by the group.
         */
        val type: CallType
    ) : CallsScreenListItem()

    /**
     * Single call item.
     */
    class CallItem(

        /**
         * Call data.
         */
        val callModel: CallsScreenCallModel,

        /**
         * Type of call.
         */
        val type: CallType
    ) : CallsScreenListItem() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is CallItem) return false

            if (callModel.id != other.callModel.id) return false

            return true
        }

        override fun hashCode(): Int {
            return callModel.id.hashCode()
        }
    }
}
