package com.deniz.recipes.controller

import com.deniz.recipes.controller.resource.CartResponse
import com.deniz.recipes.converter.CartToCartResponseConverter
import com.deniz.recipes.service.CartService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carts")
class CartsController(private val cartService: CartService) {

    private val logger = LoggerFactory.getLogger(CartsController::class.java)

    @GetMapping("/{id}")
    fun getCartById(@PathVariable id: Long): CartResponse {
        logger.info("Fetching cart with id $id")
        val cart = cartService.getCartById(id)
        return CartToCartResponseConverter.convert(cart)
    }

    @PostMapping("/{id}/add_recipe")
    fun addRecipeToCart(@PathVariable id: Long, @RequestParam recipeId: Long) {
        logger.info("Adding recipe $recipeId to cart $id")
        cartService.addRecipeToCart(id, recipeId)
    }

    @DeleteMapping("/{cartId}/recipes/{recipeId}")
    fun removeRecipeFromCart(
        @PathVariable cartId: Long,
        @PathVariable recipeId: Long
    ) {
        logger.info("Removing recipe $recipeId from cart $cartId")
        cartService.removeRecipeFromCart(cartId, recipeId)
    }
}
