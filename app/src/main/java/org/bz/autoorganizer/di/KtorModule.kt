package org.bz.autoorganizer.di

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.Charsets
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.gson.gson
import okhttp3.Cache
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.TlsVersion
import org.bz.autoorganizer.data.http.ConnectivityInterceptor
import org.bz.autoorganizer.root.extensions.logDebugCustomTag
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

private const val TIME_OUT = 1_800_000L

val ktorModule = module {

    val connectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).apply {
        tlsVersions(TlsVersion.TLS_1_2)
        cipherSuites(
            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
        )
    }.build()

    fun initKTorClient(
        context: Context,
        connectivityInterceptor: ConnectivityInterceptor
    ) = HttpClient(OkHttp) {
        val cacheSize = (10 * 1024 * 1024).toLong()
        val httpCacheDirectory = File(context.cacheDir, "offlineCache")
        val httpCache = Cache(httpCacheDirectory, cacheSize)

        install(WebSockets) {
//            pingInterval = Duration.ofSeconds(10).toMillis()
            maxFrameSize = Long.MAX_VALUE
        }

        install(ContentNegotiation) {
            gson()
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    logDebugCustomTag("KTor", "Logger KTor => $message")
                }
            }
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
            connectTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
            socketTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
        }

        engine {
            // HTTP configs
            config {
                followRedirects(true)

//                connectionSpecs(Collections.singletonList(connectionSpec))
                connectionSpecs(listOf(ConnectionSpec.CLEARTEXT, connectionSpec))
            }
            addInterceptor(connectivityInterceptor)

            clientCacheSize = (10 * 1024 * 1024)
        }

        Charsets {
            register(Charsets.UTF_8)
            register(Charsets.ISO_8859_1, quality = 0.1f)

            responseCharsetFallback = Charsets.UTF_8
        }
    }

    factory { ConnectivityInterceptor(context = androidContext()) }

    single {
        initKTorClient(
            context = androidContext(),
            connectivityInterceptor = get()
        )
    }
}