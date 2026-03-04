package com.ina17.android_architecture.features.hero.data.repository

import com.ina17.android_architecture.features.hero.data.remote.OpenDotaApi
import com.ina17.android_architecture.features.hero.data.remote.toDomain
import com.ina17.android_architecture.features.hero.domain.model.Hero
import com.ina17.android_architecture.features.hero.domain.repository.HeroRepository
import javax.inject.Inject

class HeroRepositoryImpl @Inject constructor(
    private val api: OpenDotaApi
) : HeroRepository {
    override suspend fun getHeroes(): Result<List<Hero>> {
        return try {
            val response = api.getHeroes()
            val heroes = response.map{
                it.toDomain()
            }
            Result.success(heroes)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getHerobyId(id: Int): Result<Hero>{
        return try {
            val response = api.getHeroes()
            val heroDto = response.find{it.id == id }

            if (heroDto != null) {
                Result.success(heroDto.toDomain())
            }else {
                Result.failure(Exception("Hero not found"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}