package org.bz.autoorganizer.data.repository

import org.bz.autoorganizer.data.db.RoomDB
import org.bz.autoorganizer.data.db.entity.Car
import org.bz.autoorganizer.data.db.entity.Odometer
import org.bz.autoorganizer.data.http.Either
import org.bz.autoorganizer.data.http.safeRequest

class DataBaseRepository(
    private val roomDataBase: RoomDB
) : DataBase {
    override suspend fun clearAllData(): Either<Exception, Unit> =
        safeRequest { roomDataBase.clearAllTables() }

    override suspend fun insertCar(car: Car): Either<Exception, Unit> =
        safeRequest { roomDataBase.carDao().insert(car) }

    override suspend fun insertCars(cars: List<Car>): Either<Exception, Unit> =
        safeRequest { roomDataBase.carDao().insert(*cars.toTypedArray()) }

    override suspend fun deleteCar(car: Car): Either<Exception, Unit> =
        safeRequest { roomDataBase.carDao().delete(car) }

    override suspend fun updateCar(cars: List<Car>): Either<Exception, Unit> =
        safeRequest { roomDataBase.carDao().update(*cars.toTypedArray()) }

    override suspend fun getCars(): Either<Exception, List<Car>> =
        safeRequest { roomDataBase.carDao().getCarInfo() }

    override suspend fun getCarByModel(model: String): Either<Exception, Car> =
        safeRequest { roomDataBase.carDao().getCarByModel(model) }

    override suspend fun insertOdometer(odometer: Odometer): Either<Exception, Unit> =
        safeRequest { roomDataBase.odometerDao().insert(odometer) }

    override suspend fun deleteOdometer(odometer: Odometer): Either<Exception, Unit> =
        safeRequest { roomDataBase.odometerDao().delete(odometer) }

    override suspend fun updateOdometer(odometers: List<Odometer>): Either<Exception, Unit> =
        safeRequest { roomDataBase.odometerDao().insert(*odometers.toTypedArray()) }

    override suspend fun getAllRecordsFromOdometer(): Either<Exception, List<Odometer>> =
        safeRequest { roomDataBase.odometerDao().getAllRecords() }

    override suspend fun getOdometerByCarId(carId: String): Either<Exception, Odometer> =
        safeRequest { roomDataBase.odometerDao().getOdometerByCarId(carId) }

    override suspend fun getOdometerByCarModel(model: String): Either<Exception, Int> =
        safeRequest { roomDataBase.odometerDao().getOdometerByCarModel(model) }
}