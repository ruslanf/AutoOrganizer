package org.bz.autoorganizer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.bz.autoorganizer.data.db.dao.CarDao
import org.bz.autoorganizer.data.db.dao.OdometerDao
import org.bz.autoorganizer.data.db.entity.Car
import org.bz.autoorganizer.data.db.entity.Odometer
import org.bz.autoorganizer.root.Constants.DB_NAME
import kotlin.concurrent.Volatile

@Database(
    entities = [
        Car::class,
        Odometer::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {

    abstract fun carDao() : CarDao
    abstract fun odometerDao() : OdometerDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDataBase(context: Context): RoomDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: createDB(context).also { INSTANCE = it }
            }

        private fun createDB(context: Context) =
            Room
                .databaseBuilder(context.applicationContext, RoomDB::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}