package com.hinuri.youtubesearchapp.di

import com.hinuri.youtubesearchapp.ui.SearchViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { SearchViewModel(get(), get()) }
}