package com.hellodiffa.myapplication.di

import com.hellodiffa.myapplication.data.BigBoxAPI
import com.hellodiffa.myapplication.data.source.OtpRepositoryImpl
import com.hellodiffa.myapplication.ui.MainViewModel
import com.hellodiffa.myapplication.utils.Dispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MainViewModel(get(), get()) }

    single { Dispatcher.DEFAULT }
    factory { OtpRepositoryImpl(get()) }

    single { BigBoxAPI.INSTANCE }

}