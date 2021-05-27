package com.rezapour.carstask.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.rezapour.carstask.R
import com.rezapour.carstask.adapter.CarsLIstAdapter
import com.rezapour.carstask.assests.Constants
import com.rezapour.carstask.business.model.CarModel
import com.rezapour.carstask.utils.IconUtil.vectorBitmap
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

    private val googleApiClient: GoogleApiClient? = null
    val REQUEST_LOCATION = 199

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

        enableLoc()

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
        setDefaultLocation()
    }

    private fun addItemstoList(list: List<CarModel>) {

        carsAdapter.addItems(list)
        carsAdapter.notifyDataSetChanged()
    }

    private fun addLocationsToMap(listcars: List<CarModel>) {

        for (car: CarModel in listcars) {
            val carspoint = LatLng(car.latitude, car.longitude)
            addPointstoMap(carspoint, car.name)
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 14f))
    }


    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    }


    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            mMap.isMyLocationEnabled = true
            GpsState()
        } else
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

    private fun showProgressbar() {
        progess.visibility = View.VISIBLE
    }

    private fun hideProgressbar() {
        progess.visibility = View.GONE
    }

    private fun setDefaultLocation() {
        val point = LatLng(48.134557, 11.576921)
        chnageMapCameraPosition(point)
    }


    private fun checkGpsStatus(context: Context): Boolean {
        val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun GpsState() {
        if (!checkGpsStatus(this)) {
            val snackbar = Snackbar
                .make(coordinator, "Gps is off, you can turn it on from here", Snackbar.LENGTH_LONG)
                .setAction("Turn On") {
                    val intent = Intent();
                    intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                    startActivity(intent);
                }
            snackbar.show()
        }

    }

}