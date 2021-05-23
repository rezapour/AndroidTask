package com.rezapour.carstask.repository

import com.rezapour.carstask.business.model.CarModel
import com.rezapour.carstask.utils.UiState
import retrofit2.Response

interface MainRepository {
        suspend fun getCars():UiState<List<CarModel>>
}