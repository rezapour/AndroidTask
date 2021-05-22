package com.rezapour.carstask.data.network

import com.rezapour.carstask.data.network.model.CarNetwrokEntity
import retrofit2.Response

class ApiHelperImpl constructor(val apiService: ApiService) : ApiService {
    override suspend fun getCars(): Response<List<CarNetwrokEntity>> {
        return apiService.getCars()
    }
}