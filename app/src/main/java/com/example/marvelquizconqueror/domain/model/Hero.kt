package com.example.marvelquizconqueror.domain.model

data class Hero(
    val id: String,
    val name: String,
    val role: String,
    val description: String,
    val stats: String,
    val powerLevel: Int,
    val imageResId: Int? = null,
    val isLocked: Boolean = false
)
