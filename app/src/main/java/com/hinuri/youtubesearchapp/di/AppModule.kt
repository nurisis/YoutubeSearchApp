package com.hinuri.youtubesearchapp.di

import com.hinuri.data.repository.YoutubeRepository
import com.hinuri.youtubesearchapp.domain.GetVideoUseCase
import com.hinuri.youtubesearchapp.domain.SearchVideoUseCase
import org.koin.dsl.module

val appModule = module {
    single { YoutubeRepository(get()) }

    single { GetVideoUseCase(get()) }
    single { SearchVideoUseCase(get()) }
}