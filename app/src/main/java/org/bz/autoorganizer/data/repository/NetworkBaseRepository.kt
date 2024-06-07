package org.bz.autoorganizer.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.bz.autoorganizer.data.http.Either
import org.bz.autoorganizer.data.http.safeRequest
import org.bz.autoorganizer.data.models.Auto

class NetworkBaseRepository(
    private val client: HttpClient
) : NetworkBase {

    override suspend fun getAutoModels(): Either<Exception, List<Auto>> =
        safeRequest {
            client.get("https://cars-base.ru/api/cars?full=1") {
                contentType(ContentType.Application.Json)
            }.body()
        }
}