package com.rezapour.carstask.data.network

import com.rezapour.carstask.business.model.CarModel
import com.rezapour.carstask.data.network.model.CarNetwrokEntity
import com.rezapour.carstask.utils.ModelMapper
import javax.inject.Inject


class NetworkModelMapper @Inject constructor() : ModelMapper<CarNetwrokEntity, CarModel> {
    override fun mapFromEntity(entity: CarNetwrokEntity): CarModel {
        return CarModel(
            id = entity.id,
            modelIdentifier = entity.modelIdentifier,
            modelName = entity.modelName,
            name = entity.name,
            make = entity.make,
            group = entity.group,
            color = entity.color,
            series = entity.series,
            fuelLevel = entity.fuelType,
            fuelType = entity.fuelType,
            transmission = entity.transmission,
            licensePlate = entity.licensePlate,
            latitude = entity.latitude,
            longitude = entity.longitude,
            innerCleanliness = entity.innerCleanliness,
            carImageUrl = entity.carImageUrl
        )
    }

    fun mapfromEntityList(entityList: List<CarNetwrokEntity>): List<CarModel> {
        return entityList.map { mapFromEntity(it) }
    }

}