package com.rezapour.carstask.data.network

import com.rezapour.carstask.data.network.model.CarNetwrokEntity
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("cars")
    suspend fun getCars(): Response<List<CarNetwrokEntity>>
}