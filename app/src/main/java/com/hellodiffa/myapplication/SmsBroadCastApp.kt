package com.hellodiffa.myapplication

import android.app.Application
import com.hellodiffa.myapplication.di.appModule
import org.koin.core.context.startKoin

class SmsBroadCastApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            this@SmsBroadCastApp
            modules(appModule)
        }
    }
}