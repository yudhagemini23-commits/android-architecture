package com.ina17.android_architecture.features.hero.data.remote

import retrofit2.http.GET

interface OpenDotaApi {
    @GET("api/heroStats")
    suspend fun getHeroes(): List<HeroDto>
}