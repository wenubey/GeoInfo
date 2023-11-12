package com.wenubey.countryapp.utils

sealed class Resource<out T> {
    object Loading: Resource<Nothing>()

    data class Success<out T>(val data: T?): Resource<T>()

    data class Error(val error: Exception): Resource<Nothing>()
}