package com.alenskaya.fakecalls.domain

sealed class Result<T> {
    class Success<T>(val data: T): Result<T>()
    class Error<T>(): Result<T>()
}
