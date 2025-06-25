package com.deniz.recipes.converter

import com.deniz.recipes.controller.resource.*
import com.deniz.recipes.model.Cart

object CartToCartResponseConverter {
    fun convert(cart: Cart): CartResponse {
        return CartResponse(
            id = cart.id,
            totalInCents = cart.totalInCents,
            items = cart.items.map { item ->
                CartItemResponse(
                    id = item.id,
                    cartId = item.cartId,
                    product = ProductResponse(
                        id = item.product.id,
                        name = item.product.name,
                        priceInCents = item.product.priceInCents
                    )
                )
            }
        )
    }
}
