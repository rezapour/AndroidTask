package com.rezapour.carstask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.rezapour.carstask.R
import com.rezapour.carstask.business.model.CarModel
import com.rezapour.carstask.utils.UiState
import com.rezapour.carstask.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewmodel: MainViewModel by viewModels()
    lateinit var tv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.textviewss)
        subscriveToObserver()
        viewmodel.getCars()

    }


    fun subscriveToObserver() {
        viewmodel.carsList.observe(this) { state ->
            when (state) {
                is UiState.Success<List<CarModel>> -> tv.text = state.data.get(0).make
                is UiState.Error -> tv.text = state.message
                is UiState.Loading -> tv.text = "loading"
            }
        }

    }
}