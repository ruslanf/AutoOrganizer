package org.bz.autoorganizer.data.http

import com.google.gson.Gson

sealed class Either<out L, out R> {
    companion object {
        inline fun <R> of(action: () -> R): Either<Exception, R> =
            try {
                Right(action())
            } catch (ex: Exception) {
                Left(ex)
            }
    }
}

data class Right<out R>(val value: R) : Either<Nothing, R>()
data class Left<out L>(val value: L) : Either<L, Nothing>()

private val gson by lazy { Gson() }

suspend fun <T> safeRequest(request: suspend () -> T): Either<Exception, T> =
    Either.of { request() }
