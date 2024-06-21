package org.bz.autoorganizer.di

import org.bz.autoorganizer.ui.autostate.AutoStateViewModel
import org.bz.autoorganizer.ui.profile.ProfileViewModel
import org.bz.autoorganizer.ui.profile.auto.AddNewAutoViewModel
import org.bz.autoorganizer.ui.profile.yandex.YandexOAuthViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AutoStateViewModel() }
    viewModel {
        ProfileViewModel(
            dataBase = get(),
            networkBase = get()
        )
    }
    viewModel {
        AddNewAutoViewModel(
            dataBase = get(),
            networkBase = get()
        )
    }
    viewModel {
        YandexOAuthViewModel(
            app = androidApplication()
        )
    }
}