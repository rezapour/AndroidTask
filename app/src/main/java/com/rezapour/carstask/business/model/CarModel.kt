package com.rezapour.carstask.business.modelf

data class CarModel constructor(
    val id: String,
    val modelIdentifier: String,
    val modelName: String,
    val name: String,
    val make: String,
    val group: String,
    val color: String,
    val series: String,
    val fuelType: String,
    val fuelLevel: String,
    val transmission: String,
    val licensePlate: String,
    val latitude: Long,
    val longitude: Long,
    val innerCleanliness: String,
    val carImageUrl: String
) {
}