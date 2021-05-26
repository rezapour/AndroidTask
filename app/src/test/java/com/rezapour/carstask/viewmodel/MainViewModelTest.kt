package com.rezapour.carstask.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValueTest
import com.rezapour.carstask.repository.FakeRepository
import com.google.common.truth.Truth.assertThat
import com.rezapour.carstask.business.model.CarModel
import com.rezapour.carstask.util.MainCoroutineRule
import com.rezapour.carstask.utils.UiState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: FakeRepository

    @Before
    fun setup() {
        repository = FakeRepository()
        viewModel = MainViewModel(repository)
    }

    @Test
    fun getCarsTestError() {
        repository.setNetworkRespondError(true)
        viewModel.getCars()
        val value = viewModel.carsList.getOrAwaitValueTest()
        assertThat(value).isInstanceOf(UiState.Error::class.java)

    }

    @Test
    fun getCarsTestSucces() {
        repository.setNetworkRespondError(false)
        viewModel.getCars()
        val value = viewModel.carsList.getOrAwaitValueTest()
        assertThat(value).isInstanceOf(UiState.Success::class.java)
    }

}