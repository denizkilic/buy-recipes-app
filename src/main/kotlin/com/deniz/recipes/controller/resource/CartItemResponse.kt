package com.deniz.recipes.controller.resource

data class CartItemResponse(
    val id: Long,
    val cartId: Long,
    val product: ProductResponse
)