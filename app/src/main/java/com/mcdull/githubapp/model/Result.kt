package com.mcdull.githubapp.model

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable? = null, val message: String? = null) :
        Result<Nothing>()

    object Loading : Result<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun loading() = Loading
        fun error(exception: Throwable) = Error(exception, exception.message)
        fun error(message: String) = Error(message = message)
    }
}