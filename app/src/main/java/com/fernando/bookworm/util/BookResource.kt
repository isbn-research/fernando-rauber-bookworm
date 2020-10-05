package com.fernando.bookworm.util

import androidx.annotation.StringRes

class BookResource<T>(val status: AuthStatus, val data: T?, val message: Int?) {
    enum class AuthStatus {
        ERROR, LOADING, NOT_FOUND, SUCCESS
    }

    companion object {

        fun <T> success(data: T?): BookResource<T> {
            return BookResource(AuthStatus.SUCCESS, data, null)
        }

        fun <T> error(@StringRes msg: Int): BookResource<T> {
            return BookResource(AuthStatus.ERROR, null, msg)
        }

        fun <T> loading(): BookResource<T> {
            return BookResource(AuthStatus.LOADING, null, null)
        }

        fun <T> notFound(): BookResource<T> {
            return BookResource(AuthStatus.NOT_FOUND, null, null)
        }
    }
}