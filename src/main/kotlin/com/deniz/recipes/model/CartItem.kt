package com.deniz.recipes.model

data class CartItem(
    val id: Long,
    val cartId: Long,
    val product: Product
)