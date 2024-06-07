package org.bz.autoorganizer.di

object KoinModules {

    fun getAllModules() = listOf(
        repositoryModule,
        databaseModule,
        viewModelModule,
        ktorModule
    )
}
