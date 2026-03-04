package com.ina17.android_architecture.features.hero.domain.model

data class Hero(
    val id: Int,
    val localizedName: String?,
    val primaryAttr: String?,
    val attackType: String?,
    val img: String?,
    val icon: String?
)
