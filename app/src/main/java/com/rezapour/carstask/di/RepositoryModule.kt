package com.rezapour.carstask.di

import com.rezapour.carstask.data.network.ApiHelper
import com.rezapour.carstask.data.network.ApiHelperImpl
import com.rezapour.carstask.data.network.ApiService
import com.rezapour.carstask.data.network.NetworkModelMapper
import com.rezapour.carstask.repository.MainRepository
import com.rezapour.carstask.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideApiHelper(apiService: ApiService): ApiHelper {
        return ApiHelperImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideRepository(apiHelper: ApiHelper, networkMapper: NetworkModelMapper): MainRepository {
        return MainRepositoryImpl(apiHelper, networkMapper)
    }

}