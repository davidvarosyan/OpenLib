package com.test.openlib.domain.core

suspend fun <T> runSafe(block: suspend () -> T): Result<T> {
    return try {
        Result.Success(block.invoke())
    } catch (ex: Exception) {
        Result.Error(ex)
    }
}
