package com.rezapour.carstask.repository

import com.rezapour.carstask.business.model.CarModel
import com.rezapour.carstask.data.network.ApiHelper
import com.rezapour.carstask.data.network.NetworkModelMapper
import com.rezapour.carstask.utils.ModelMapper
import com.rezapour.carstask.utils.UiState
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class MainRepositoryImpl
@Inject constructor(
    private val apiHelper: ApiHelper,
    private val mapper: NetworkModelMapper
) : MainRepository {

    override suspend fun getCars(): UiState<List<CarModel>> {

        return try {
            val respond = apiHelper.getCars()
            if (respond.isSuccessful()) {
                respond.body()?.let {
                    return@let UiState.Success(mapper.mapfromEntityList(it))
                } ?: return UiState.Error("There is no data to show.")
            } else
                return UiState.Error("there is a problem with server")
        } catch (e: Exception) {
            return UiState.Error("Couldn't reach to server. Check your internet connection.")
        }


    }
}