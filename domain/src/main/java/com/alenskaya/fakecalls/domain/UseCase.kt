package com.alenskaya.fakecalls.domain

/**
 * Base interface of use cases.
 * @param T - type of expected use case result.
 */
interface UseCase<T> {
    suspend operator fun invoke(): T
}
