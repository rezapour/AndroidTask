package com.rezapour.carstask.data.network

import com.rezapour.carstask.data.network.model.CarNetwrokEntity
import retrofit2.Response


interface ApiHelper {

   suspend fun getCars(): Response<List<CarNetwrokEntity>>

}