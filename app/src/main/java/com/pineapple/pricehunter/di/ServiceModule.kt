package com.pineapple.pricehunter.di

import com.pineapple.pricehunter.model.service.DbService
import com.pineapple.pricehunter.model.service.impl.DbServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ServiceModule {

    @Provides
    fun provideDbService(): DbService = DbServiceImpl()
}