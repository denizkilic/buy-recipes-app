package com.deniz.recipes.controller.resource

data class CartResponse(
    val id: Long,
    val totalInCents: Int,
    val items: List<CartItemResponse>
)