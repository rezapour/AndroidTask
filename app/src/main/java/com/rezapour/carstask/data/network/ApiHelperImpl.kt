package com.rezapour.carstask.data.network

import com.rezapour.carstask.data.network.model.CarNetwrokEntity
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getCars(): Response<List<CarNetwrokEntity>> {
        return apiService.getCars()
    }
}