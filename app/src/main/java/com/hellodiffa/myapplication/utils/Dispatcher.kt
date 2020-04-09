package com.hellodiffa.myapplication.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

interface Dispatcher {
    fun backgroundExecutor(): CoroutineDispatcher
    fun mainExecutor(): CoroutineDispatcher

    companion object {

        val DEFAULT: Dispatcher = object : Dispatcher {
            override fun backgroundExecutor(): CoroutineDispatcher {
                return IO
            }

            override fun mainExecutor(): CoroutineDispatcher {
                return Main
            }
        }
    }
}