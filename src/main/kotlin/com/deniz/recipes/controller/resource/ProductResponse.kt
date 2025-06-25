package com.deniz.recipes.controller.resource

data class ProductResponse(
    val id: Long,
    val name: String,
    val priceInCents: Int
)