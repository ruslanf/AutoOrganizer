package org.bz.autoorganizer.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "car",
    indices = [Index(value = ["makes", "manufacturer", "model"], unique = true)]
)
data class Car(
    @PrimaryKey
    @ColumnInfo(name = "car_id") var carId: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "makes") val makes: String?,
    @ColumnInfo(name = "manufacturer") val manufacturer: String?,
    @ColumnInfo(name = "model") val model: String?
)