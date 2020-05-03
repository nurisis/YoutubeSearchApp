package com.hinuri.youtubesearchapp.di

import com.hinuri.data.repository.YoutubeRepository
import org.koin.dsl.module

val appModule = module {
    single { YoutubeRepository(get()) }
}