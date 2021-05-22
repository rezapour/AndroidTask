package com.rezapour.carstask.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CarNetwrokEntity constructor(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("modelIdentifier")
    @Expose
    val modelIdentifier: String,

    @SerializedName("modelName")
    @Expose
    val modelName: String,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("make")
    @Expose
    val make: String,

    @SerializedName("group")
    @Expose
    val group: String,

    @SerializedName("color")
    @Expose
    val color: String,

    @SerializedName("series")
    @Expose
    val series: String,

    @SerializedName("fuelType")
    @Expose
    val fuelType: String,

    @SerializedName("fuelLevel")
    @Expose
    val fuelLevel: String,

    @SerializedName("transmission")
    @Expose
    val transmission: String,

    @SerializedName("licensePlate")
    @Expose
    val licensePlate: String,

    @SerializedName("latitude")
    @Expose
    val latitude: Long,

    @SerializedName("longitude")
    @Expose
    val longitude: Long,

    @SerializedName("innerCleanliness")
    @Expose
    val innerCleanliness: String,

    @SerializedName("carImageUrl")
    @Expose
    val carImageUrl: String
)