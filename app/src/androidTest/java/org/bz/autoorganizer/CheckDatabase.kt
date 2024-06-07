package org.bz.autoorganizer

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.bz.autoorganizer.data.db.entity.Car
import org.bz.autoorganizer.data.db.entity.Odometer
import org.bz.autoorganizer.data.http.Left
import org.bz.autoorganizer.data.http.Right
import org.bz.autoorganizer.data.repository.DataBase
import org.bz.autoorganizer.di.databaseModule
import org.bz.autoorganizer.di.repositoryModule
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.io.IOException
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class CheckDatabase : KoinTest {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val databaseRepository by inject<DataBase>()

    private val car = Car(
        makes = "Volvo",
        model = "XC90",
        manufacturer = "Volvo"
    )

    private var carId = UUID.randomUUID().toString()

    private val odometer = Odometer(
        odometer = 249000,
        carId = carId
    )

    private val cars = listOf(
        Car(
            makes = "Volvo",
            manufacturer = "Volvo",
            model = "C30"
        ),
        Car(
            makes = "Volvo",
            manufacturer = "Volvo",
            model = "S60"
        ),
        Car(
            makes = "Volvo",
            manufacturer = "Volvo",
            model = "XC60"
        ),
        Car(
            makes = "Volvo",
            manufacturer = "Volvo",
            model = "XC90"
        )
    )

    @Before
    fun before() {
        stopKoin()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(
                listOf(
                    databaseModule,
//                    databaseTestModule,
                    repositoryModule
                )
            )
        }
        loadKoinModules(
            listOf(
                databaseModule,
//                databaseTestModule,
                repositoryModule
            )
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        unloadKoinModules(
            listOf(
                databaseModule,
//                databaseTestModule,
                repositoryModule
            )
        )
        stopKoin()
    }

    @Test
    fun testClearAllTables() {
        testScope.runTest {
            launch { clearAllTables() }
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeCarAndReadInList() {
        testScope.runTest {
            launch { insertRecordIntoCar(car) }
            launch { readRecordFromCar() }
        }
    }

    @Test
    fun writeAndReadAllRecordsFromOdometer() {
        testScope.runTest {
            launch { insertRecordIntoOdometer() }
            launch { readAllRecordsFromOdometer() }
        }
    }

    @Test
    fun writeAndReadFromOdometer() {
        testScope.runTest {
            launch { insertRecordIntoCar(car) }
            launch { insertRecordIntoOdometer() }
            launch { readRecordFromCar() }
            launch { readRecordFromOdometerByCarModel() }
        }
    }

    @Test
    fun testGetOdometerById() {
        testScope.launch {
            launch { insertRecordIntoOdometer() }
            launch { readRecordFromOdometerById() }
        }
    }

    @Test
    fun testAllRecordsFromCar() {
        testScope.runTest {
            launch { insertRecordsIntoCar() }
            launch { getAllRecordsFromCar() }
        }
    }

    private suspend fun clearAllTables() {
        when (val r = databaseRepository.clearAllData()) {
            is Left -> assertFails { r.value }
            is Right -> assertSame(r.value, Unit)
        }
    }

    private suspend fun insertRecordIntoCar(car: Car) {
        when (val r = databaseRepository.insertCar(car)) {
            is Left -> assertFails { r.value }
            is Right -> {
                assertTrue("Inserted row in Car entity ") { r.value.toString().isNotBlank() }
                readRecordFromCar()
            }
        }
    }

    private suspend fun insertRecordIntoOdometer() {
        val odometer = Odometer(
            odometer = 249000,
            carId = carId
        )
        when (val r = databaseRepository.insertOdometer(odometer)) {
            is Left -> assertFails { r.value }
            is Right ->
                assertTrue("Inserted row in Odometer entity ") { r.value.toString().isNotBlank() }
        }
    }

    private suspend fun readRecordFromCar() {
        when (val r = databaseRepository.getCarByModel("XC90")) {
            is Left -> assertFails { r.value }
            is Right -> {
                carId = r.value.carId
                println("Car => ${r.value}")
                assertNotNull(r.value)
                assertEquals(r.value.manufacturer, car.manufacturer, "Read from Car successfully ")
                val model = r.value.model
                assertTrue { model.equals("XC90") }
                assertFalse { model.equals("xc90") }
                assertEquals("XC90", model)
            }
        }
    }

    private suspend fun readAllRecordsFromOdometer() {
        when (val r = databaseRepository.getAllRecordsFromOdometer()) {
            is Left -> assertFails { r.value }
            is Right -> {
                assertNotNull(r.value)
                val odometers = r.value
//                assertContains(odometers, odometer)
                assertTrue { odometers[0].odometer == odometer.odometer }
            }
        }
    }

    private suspend fun readRecordFromOdometerByCarModel() {
        when (val r = databaseRepository.getOdometerByCarModel("XC90")) {
            is Left -> assertFails { r.value }
            is Right -> {
                println("odometer => ${r.value}")
                assertNotNull(r.value)
                assertEquals(
                    r.value,
                    odometer.odometer,
                    "Read from Odometer value => ${r.value}, odometer => ${odometer.odometer}"
                )
                val odometer = r.value
                assertTrue { odometer == 249000 }
                assertFalse { odometer == 250000 }
            }
        }
    }

    private suspend fun readRecordFromOdometerById() {
        carId?.also { id ->
            when (val r = databaseRepository.getOdometerByCarId(id)) {
                is Left -> assertFails { r.value }
                is Right -> {
                    println("odometer => ${r.value}")
                    assertNotNull(r.value)
                    assertEquals(
                        r.value,
                        odometer,
                        "Read from Odometer value => ${r.value}, odometer => ${odometer.odometer}"
                    )
                    val odometer = r.value.odometer
                    assertTrue { odometer == 249000 }
                    assertFalse { odometer == 250000 }
                }
            }
        }
    }

    private suspend fun insertRecordsIntoCar() {
        when (val r = databaseRepository.insertCars(cars)) {
            is Left -> assertFails { r.value }
            is Right -> {
                assertTrue("Inserted rows in Car entity ") { r.value.toString().isNotBlank() }
                getAllRecordsFromCar()
            }
        }
    }

    private suspend fun getAllRecordsFromCar() {
        when (val r = databaseRepository.getCars()) {
            is Left -> assertFails { r.value }
            is Right -> {
                val cars = r.value
                cars.forEach { println("Car => $it") }
                assertNotNull(cars)
                assertTrue { cars[0].model == car.model }
//                assertContains(cars, car)
//                assertTrue { cars.contains(car) }
            }
        }
    }
}