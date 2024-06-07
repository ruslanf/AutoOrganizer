package org.bz.autoorganizer.data.repository

import org.bz.autoorganizer.data.db.entity.Car
import org.bz.autoorganizer.data.db.entity.Odometer
import org.bz.autoorganizer.data.http.Either

interface DataBase {
    /**
     * Clear all data in DB
     */
    suspend fun clearAllData(): Either<Exception, Unit>

    /**
     * Work with car
     */
    suspend fun insertCar(car: Car): Either<Exception, Unit>
    suspend fun insertCars(cars: List<Car>): Either<Exception, Unit>
    suspend fun deleteCar(car: Car): Either<Exception, Unit>
    suspend fun updateCar(cars: List<Car>): Either<Exception, Unit>

    suspend fun getCars(): Either<Exception, List<Car>>
    suspend fun getCarByModel(model: String): Either<Exception, Car>

    /**
     * Work with odometer
     */
    suspend fun insertOdometer(odometer: Odometer): Either<Exception, Unit>
    suspend fun deleteOdometer(odometer: Odometer): Either<Exception, Unit>
    suspend fun updateOdometer(odometers: List<Odometer>): Either<Exception, Unit>

    suspend fun getAllRecordsFromOdometer(): Either<Exception, List<Odometer>>
    suspend fun getOdometerByCarId(carId: String): Either<Exception, Odometer>
    suspend fun getOdometerByCarModel(model: String): Either<Exception, Int>
}