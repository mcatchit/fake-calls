package com.alenskaya.fakecalls.presentation.components.swipe

/**
 * Direction of swipe
 */
enum class SwipeDirection {
    RIGHT,
    LEFT;

    fun opposite() = if (this == RIGHT) LEFT else RIGHT
}
