package com.deniz.recipes.controller.resource

data class RecipeResponse(
    val id: Long,
    val name: String,
    val ingredients: List<ProductResponse>
)