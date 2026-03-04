package com.ina17.android_architecture.features.hero.domain.repository

import com.ina17.android_architecture.features.hero.domain.model.Hero

interface HeroRepository {
    suspend fun getHeroes(): Result<List<Hero>>

    suspend fun getHerobyId(id: Int): Result<Hero>
}