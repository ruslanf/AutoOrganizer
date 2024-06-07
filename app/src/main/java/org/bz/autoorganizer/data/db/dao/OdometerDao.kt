package org.bz.autoorganizer.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import org.bz.autoorganizer.data.db.entity.Odometer

@Dao
interface OdometerDao : BaseDao<Odometer> {

    @Query("select * from odometer")
    suspend fun getAllRecords(): List<Odometer>

    @Query("select odometer from odometer left join car on car.car_id = odometer.car_id where car.model = :model")
    suspend fun getOdometerByCarModel(model: String): Int

    @Transaction
    @Query("select * from odometer where car_id = :carId")
    suspend fun getOdometerByCarId(carId: String): Odometer
}