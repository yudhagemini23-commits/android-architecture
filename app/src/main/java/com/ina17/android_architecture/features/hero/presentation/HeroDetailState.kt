package com.ina17.android_architecture.features.hero.presentation

import com.ina17.android_architecture.features.hero.domain.model.Hero

sealed class HeroDetailState {
    object Loading : HeroDetailState()
    data class Success(val hero: Hero) : HeroDetailState()
    data class Error(val message: String) : HeroDetailState()
}