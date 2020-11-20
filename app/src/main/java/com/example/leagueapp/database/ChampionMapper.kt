package com.example.leagueapp.database

import com.example.leagueapp.model.ChampionResponse

fun ChampionEntity.toChampion() = ChampionResponse.Champion(
        this.id,
        this.key,
        this.name,
        this.title,
        ChampionResponse.Champion.Image(this.full),
        this.tags?.split(","),
        true
)

fun List<ChampionEntity>.toChampionList() = this.map { it.toChampion() }

fun ChampionResponse.Champion.toChampionEntity() = ChampionEntity(
        this.id,
        this.key,
        this.name,
        this.title,
        this.image!!.full,
        this.tags?.joinToString(",") { it }
)