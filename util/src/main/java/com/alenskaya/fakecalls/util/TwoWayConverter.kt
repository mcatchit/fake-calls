package com.alenskaya.fakecalls.util

/**
 * Converts an object of one type to another and supports reverse conversion.
 * @param I - input type.
 * @param O - output param.
 */
interface TwoWayConverter<I, O> {

    fun convert(input: I): O

    fun convertBack(input: O): I
}
