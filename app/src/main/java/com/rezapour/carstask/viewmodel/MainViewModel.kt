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
    private val savedStateHandle: SavedStateHandle,
    val repository: MainRepository
) : ViewModel() {

    private val _carsList: MutableLiveData<UiState<List<CarModel>>> = MutableLiveData()
    val carsList: LiveData<UiState<List<CarModel>>>
        get() = _carsList


    fun getCars() {
        _carsList.value = UiState.Loading
        viewModelScope.launch {
            try {
                _carsList.value = repository.getCars()
            } catch (e: Exception) {
                _carsList.value = UiState.Error(e.message.toString())
            }
        }
    }


}