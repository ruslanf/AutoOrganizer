package org.bz.autoorganizer.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "odometer",
    indices = [Index(value = ["odometer_id", "car_id", "odometer"], unique = true)]
)
data class Odometer(
    @PrimaryKey
    @ColumnInfo(name = "odometer_id") var odometerId: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "car_id") val carId: String?,
    @ColumnInfo(name = "odometer") val odometer: Int?,
)