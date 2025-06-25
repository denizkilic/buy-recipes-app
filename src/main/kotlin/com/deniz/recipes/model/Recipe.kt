package com.deniz.recipes.model

data class Recipe(
    val id: Long,
    val name: String,
    val ingredients: List<Product>
)
