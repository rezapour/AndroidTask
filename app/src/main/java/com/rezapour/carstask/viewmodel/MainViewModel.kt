package com.rezapour.carstask.viewmodel

import androidx.lifecycle.*
import com.rezapour.carstask.business.model.CarModel
import com.rezapour.carstask.repository.MainRepository
import com.rezapour.carstask.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _carsList: MutableLiveData<UiState<List<CarModel>>> = MutableLiveData()
    val carsList: LiveData<UiState<List<CarModel>>>
        get() = _carsList


    fun getCars() {
        _carsList.value = UiState.Loading
        viewModelScope.launch {
            _carsList.postValue(repository.getCars())
        }
    }


}