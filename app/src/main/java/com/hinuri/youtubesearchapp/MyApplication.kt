package com.hinuri.youtubesearchapp

import android.app.Application
import com.hinuri.youtubesearchapp.di.appModule
import com.hinuri.youtubesearchapp.di.networkModule
import com.hinuri.youtubesearchapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(appModule, networkModule, viewModelModule))
        }
    }
}