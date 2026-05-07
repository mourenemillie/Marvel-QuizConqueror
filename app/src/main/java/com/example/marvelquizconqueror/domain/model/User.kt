package com.example.marvelquizconqueror.domain.model

data class User(
    val name: String,
    val level: Int,
    val xp: Int,
    val coins: Int,
    val energy: Int,
    val profileImageId: Int? = null
)
