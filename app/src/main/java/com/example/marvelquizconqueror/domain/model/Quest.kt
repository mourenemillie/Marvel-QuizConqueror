package com.example.marvelquizconqueror.domain.model

data class Quest(
    val id: String,
    val orderIndex: String,
    val title: String,
    val difficultyStars: Int,
    val tags: List<String>,
    val isCompleted: Boolean = false,
    val isLocked: Boolean = false,
    val isSuperHard: Boolean = false,
    val imageResId: Int? = null
)
