package com.rezapour.carstask.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.rezapour.carstask.R
import com.rezapour.carstask.adapter.CarsLIstAdapter
import com.rezapour.carstask.assests.Constants
import com.rezapour.carstask.business.model.CarModel
import com.rezapour.carstask.utils.OnclickRecyclerviewListener
import com.rezapour.carstask.utils.UiState
import com.rezapour.carstask.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnclickRecyclerviewListener,
    View.OnClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var recyclerview: RecyclerView
    private lateinit var carsAdapter: CarsLIstAdapter
    private var listofCars: List<CarModel> = ArrayList()
    private lateinit var fram: View
    private lateinit var coordinator: View
    private lateinit var progess: ProgressBar
    private val viewmodel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        initUI()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        subscriveToObserver()

    }


    private fun initUI() {
        progess = findViewById(R.id.progress_circular)
        coordinator = findViewById(R.id.map_activity)
        fram = findViewById(R.id.frame)
        recyclerview = findViewById(R.id.recyclerview_cars)
        carsAdapter = CarsLIstAdapter(this)
        recyclerview.apply {
            layoutManager =
                LinearLayoutManager(this@MapsActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = carsAdapter
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        enableMyLocation()
        viewmodel.getCars()
    }

    private fun subscriveToObserver() {
        viewmodel.carsList.observe(this) { state ->
            when (state) {
                is UiState.Success<List<CarModel>> -> success(state.data)
                is UiState.Error -> {
                    hideProgressbar()
                    showSnackBar(state.message)
                }
                is UiState.Loading -> showProgressbar()
            }
        }

    }

    private fun success(list: List<CarModel>) {
        hideProgressbar()
        listofCars = list
        addLocationsToMap(list)
        addItemstoList(list)
    }

    private fun addItemstoList(list: List<CarModel>) {

        carsAdapter.addItems(list)
        carsAdapter.notifyDataSetChanged()
    }

    private fun addLocationsToMap(listcars: List<CarModel>) {

        for (car: CarModel in listcars) {
            val sydney = LatLng(car.latitude, car.longitude)
            addPointstoMap(sydney, car.name)
        }
    }

    private fun addPointstoMap(latlong: LatLng, name: String) {
        mMap.addMarker(
            MarkerOptions().position(latlong).title(name).icon(
                vectorBitmap(
                    this,
                    R.drawable.ic_car,
                    ContextCompat.getColor(this, R.color.green)
                )
            )
        )
    }

    private fun chnageMapCameraPosition(latlong: LatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15f))
    }

    private fun vectorBitmap(
        contex: Context,
        @DrawableRes id: Int,
        @ColorInt color: Int
    ): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(contex.resources, id, null)
        if (vectorDrawable == null) {
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)

    }


    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    }

    private fun enableMyLocation() {
        if (isPermissionGranted())
            mMap.isMyLocationEnabled = true
        else
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.REQUEST_LOCATION_PERMISSION
            )
    }

    override fun onRowListener(postion: Int) {
        val car = listofCars.get(postion)
        val carPostion = LatLng(car.latitude, car.longitude)
        chnageMapCameraPosition(carPostion)

    }

    private fun showSnackBar(message: String) {
        val snackbar = Snackbar
            .make(coordinator, message, Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry", this)
        snackbar.show()
    }

    override fun onClick(p0: View?) {
        viewmodel.getCars()
    }

    fun showProgressbar() {
        progess.visibility = View.VISIBLE
    }

    fun hideProgressbar() {
        progess.visibility = View.GONE
    }


}