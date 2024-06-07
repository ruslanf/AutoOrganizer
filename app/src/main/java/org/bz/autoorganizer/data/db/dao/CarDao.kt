package org.bz.autoorganizer.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.bz.autoorganizer.data.db.entity.Car

@Dao
interface CarDao : BaseDao<Car> {

    @Query("select * from car where model")
    suspend fun getCarInfo(): List<Car>

    @Query("select * from car where model like :model limit 1")
    suspend fun getCarByModel(model: String): Car
}