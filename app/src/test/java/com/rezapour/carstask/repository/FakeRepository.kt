package com.rezapour.carstask.repository

import com.rezapour.carstask.business.model.CarModel
import com.rezapour.carstask.repository.MainRepository
import com.rezapour.carstask.utils.UiState

class FakeRepository : MainRepository {

    private var carsList = mutableListOf<CarModel>()
    private var networkRespondError = false

    public fun setNetworkRespondError(value: Boolean) {
        networkRespondError = value
    }

    override suspend fun getCars(): UiState<List<CarModel>> {
        return if (networkRespondError)
            UiState.Error("There is a problem with reaching the server")
        else
            UiState.Success(carsList)
    }


}