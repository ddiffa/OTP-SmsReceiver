package com.hellodiffa.myapplication.common

import androidx.annotation.StringRes

sealed class ResultState<out T> {

    data class NoInternetConnection<out T>(@StringRes val errorMessage: Int) : ResultState<T>()
    class Loading<out T> : ResultState<T>()
    data class HasData<out T>(val data: T) : ResultState<T>()
    data class Error<out T>(@StringRes val errorMessage: Int) : ResultState<T>()
    data class TimeOut<out T>(@StringRes val errorMessage: Int) : ResultState<T>()

}