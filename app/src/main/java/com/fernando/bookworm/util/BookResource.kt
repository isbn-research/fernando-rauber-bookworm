package com.fernando.bookworm.util

import androidx.annotation.StringRes

sealed class BookResource<out T> {

    data class Success<out T>(val data: T) : BookResource<T>() // Status success and data of the result

    data class Error(@StringRes val msg: Int) : BookResource<Nothing>() // Status Error an error message

    object NotFound : BookResource<Nothing>()

    object Loading : BookResource<Nothing>() // Status to display loading popup

}
