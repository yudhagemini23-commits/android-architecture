package com.ina17.android_architecture.features.hero.data.remote

import com.ina17.android_architecture.features.hero.domain.model.Hero
import com.google.gson.annotations.SerializedName

data class HeroDto (
    val id: Int,
    @SerializedName("localized_name") val localizedName: String,
    @SerializedName("primary_attr") val primaryAttr: String,
    @SerializedName("attack_type") val attackType: String,
    @SerializedName("img") val img: String,
    @SerializedName("icon") val icon: String
)

fun HeroDto.toDomain(): Hero{
    val baseUrl = "https://cdn.cloudflare.steamstatic.com"
    return Hero(
        id = this.id,
        localizedName = this.localizedName,
        primaryAttr = this.primaryAttr,
        attackType = this.attackType,
        img = "$baseUrl${this.img}",
        icon = "$baseUrl${this.icon}"
    )
}