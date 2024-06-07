package org.bz.autoorganizer.data.repository

import org.bz.autoorganizer.data.http.Either
import org.bz.autoorganizer.data.models.Auto

interface NetworkBase {
    /**
     * GET list of auto models and manufacturer's name from https://cars-base.ru
     */
    suspend fun getAutoModels(): Either<Exception, List<Auto>>
}