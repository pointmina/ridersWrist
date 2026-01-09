package com.hanto.riderswrist.shared.di

import com.hanto.riderswrist.shared.data.repository.GmsWearableRepository
import com.hanto.riderswrist.shared.domain.repository.WearableRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWearableRepository(
        impl: GmsWearableRepository
    ): WearableRepository
}