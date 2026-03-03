package com.ina17.android_architecture.features.hero.data.remote

import com.ina17.android_architecture.features.hero.domain.model.Hero
import com.google.gson.annotations.SerializedName

data class HeroDto (
    val id: Int,
    @SerializedName("localized_name") val localizedName: String,
    @SerializedName("primary_att") val primaryAttr: String,
    @SerializedName("attack_type") val attackType: String
)

fun HeroDto.toDomain(): Hero{
    return Hero(
        id = this.id,
        localizedName = this.localizedName,
        primaryAttr = this.primaryAttr,
        attackType = this.attackType
    )
}