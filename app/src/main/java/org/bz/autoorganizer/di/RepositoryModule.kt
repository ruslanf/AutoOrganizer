package org.bz.autoorganizer.di

import org.bz.autoorganizer.data.repository.DataBase
import org.bz.autoorganizer.data.repository.DataBaseRepository
import org.bz.autoorganizer.data.repository.NetworkBase
import org.bz.autoorganizer.data.repository.NetworkBaseRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<DataBase> { DataBaseRepository(roomDataBase = get()) }

    single<NetworkBase> { NetworkBaseRepository(client = get()) }
}