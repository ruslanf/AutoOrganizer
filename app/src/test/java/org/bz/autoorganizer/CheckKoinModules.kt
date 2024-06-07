package org.bz.autoorganizer

import org.bz.autoorganizer.di.databaseModule
import org.bz.autoorganizer.di.databaseTestModule
import org.bz.autoorganizer.di.ktorModule
import org.bz.autoorganizer.di.repositoryModule
import org.bz.autoorganizer.di.viewModelModule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.verify.verify

class CheckKoinModules : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkRepositoryModule() {
        repositoryModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkDataBaseModule() {
        databaseModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkKtorModule() {
        ktorModule.verify(
           extraTypes = listOf(
               android.content.Context::class,
               io.ktor.client.engine.HttpClientEngine::class,
               io.ktor.client.HttpClientConfig::class
           )
        )
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkViewModelModule() {
        viewModelModule.verify()
    }

    @Test
    fun verifyKoinApp() {
        checkModules {
            listOf(
                ktorModule,
                viewModelModule,
                databaseModule,
                repositoryModule,
                databaseTestModule
            )
        }
    }
}