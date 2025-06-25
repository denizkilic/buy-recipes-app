package com.deniz.recipes.model

data class Cart(
    val id: Long,
    val totalInCents: Int,
    val items: List<CartItem> = emptyList()
)