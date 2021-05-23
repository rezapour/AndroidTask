package com.rezapour.carstask.repository

import com.rezapour.carstask.business.model.CarModel
import com.rezapour.carstask.data.network.ApiHelper
import com.rezapour.carstask.data.network.NetworkModelMapper
import com.rezapour.carstask.utils.ModelMapper
import com.rezapour.carstask.utils.UiState
import retrofit2.Response
import javax.inject.Inject

class MainRepositoryImpl
@Inject constructor(
    private val apiHelper: ApiHelper,
    private val mapper: NetworkModelMapper
) : MainRepository {

    override suspend fun getCars(): UiState<List<CarModel>> {
        val respond = apiHelper.getCars()
        if (respond.code() == 200) {
            val carsList = respond.body()
            if (carsList != null)
                return UiState.Success(mapper.mapfromEntityList(carsList))
            else
                return UiState.Error("List is empity")
        } else
            return UiState.Error("there is a problem with server")

    }
}