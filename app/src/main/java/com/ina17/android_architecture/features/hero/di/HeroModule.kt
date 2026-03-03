package com.ina17.android_architecture.features.hero.di

import com.ina17.android_architecture.features.hero.data.remote.OpenDotaApi
import com.ina17.android_architecture.features.hero.data.repository.HeroRepositoryImpl
import com.ina17.android_architecture.features.hero.domain.repository.HeroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroModule {

    @Provides
    @Singleton
    fun provideOpenDotaApi(retrofit: Retrofit): OpenDotaApi {
        return retrofit.create(OpenDotaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHeroRepository(api: OpenDotaApi): HeroRepository{
        return HeroRepositoryImpl(api)
    }
}