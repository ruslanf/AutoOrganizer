package org.bz.autoorganizer

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.bz.autoorganizer.data.http.Left
import org.bz.autoorganizer.data.http.Right
import org.bz.autoorganizer.data.models.Auto
import org.bz.autoorganizer.data.repository.NetworkBase
import org.bz.autoorganizer.di.ktorModule
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
import kotlin.streams.asStream
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class ApiTest : KoinTest {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val networkBaseRepository by inject<NetworkBase>()

    private val modelVolvo = Auto(
        id = "VOLVO",
        name = "Volvo",
        cyrillicName = "Вольво",
        isPopular = true,
        country = null,
        models = null
    )

    @Before
    fun before() {
        stopKoin()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(
                listOf(
                    ktorModule,
                    repositoryModule
                )
            )
        }
        loadKoinModules(
            listOf(
                ktorModule,
                repositoryModule
            )
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        unloadKoinModules(
            listOf(
                ktorModule,
                repositoryModule
            )
        )
        stopKoin()
    }

    @Test
    fun testGetAutoModels() {
        testScope.runTest {
            launch { getAutoModels() }
        }
    }

    private suspend fun getAutoModels() {
        when (val r = networkBaseRepository.getAutoModels()) {
            is Left -> assertFails { r.value }
            is Right -> {
                assertNotNull(r.value)
                assertTrue {
                    r.value
                        .asSequence()
                        .asStream()
                        .allMatch { modelVolvo.name == "Volvo" }
                }
            }
        }
    }
}