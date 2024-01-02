package com.wenubey.countryapp.utils

sealed class Resource<out T> {
    data object Loading: Resource<Nothing>()

    data class Success<out T>(val data: T?): Resource<T>()

    data class Error(val error: Exception): Resource<Nothing>()
}

sealed class DataResponse<out T> {
    data class Loading(val isLoading: Boolean): DataResponse<Nothing>()

    data class Success<out T>(val data: T?): DataResponse<T>()

    data class Error(val error: Exception): DataResponse<Nothing>()
}
