package org.bz.autoorganizer.di

import androidx.room.Room
import org.bz.autoorganizer.data.db.RoomDB
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { RoomDB.getDataBase(androidContext()) }
    single { get<RoomDB>().carDao() }
    single { get<RoomDB>().odometerDao() }
}

val databaseTestModule = module {
    single {
        Room
            .inMemoryDatabaseBuilder(androidApplication(), RoomDB::class.java)
            .allowMainThreadQueries()
            .build()
    }
}