package org.bz.autoorganizer.di

import org.bz.autoorganizer.ui.autostate.AutoStateViewModel
import org.bz.autoorganizer.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AutoStateViewModel() }
    viewModel { ProfileViewModel() }
}