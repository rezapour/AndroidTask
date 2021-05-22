package com.rezapour.carstask.utils

interface ModelMapper<Entity, BusinessModel> {
    fun mapFromEntity(entity: Entity): BusinessModel
}