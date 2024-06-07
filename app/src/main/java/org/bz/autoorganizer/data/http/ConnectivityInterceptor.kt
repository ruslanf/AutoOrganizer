package org.bz.autoorganizer.data.http

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        if (!NetworkService(context).isOnline())
            throw IOException("No internet connection...") else chain.proceed(chain.request())
}