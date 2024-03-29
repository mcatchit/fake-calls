package com.alenskaya.fakecalls.util

/**
 * Converts an object of one type to another.
 * @param I - input type.
 * @param O - output param.
 */
interface Converter<I, O> {
    fun convert(input: I): O
}
