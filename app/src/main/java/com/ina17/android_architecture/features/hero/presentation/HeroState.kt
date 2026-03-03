package com.ina17.android_architecture.features.hero.presentation

import com.ina17.android_architecture.features.hero.domain.model.Hero

sealed class HeroState {
    object Loading: HeroState()
    data class Success(val heroes: List<Hero>): HeroState()
    data class Error(val message: String): HeroState()
}