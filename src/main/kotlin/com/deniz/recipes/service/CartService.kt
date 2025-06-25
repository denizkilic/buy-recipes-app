package com.deniz.recipes.service

import com.deniz.recipes.model.Cart
import com.deniz.recipes.model.CartItem
import com.deniz.recipes.model.Product
import com.deniz.recipes.repo.*
import com.deniz.recipes.repo.entity.CartItemEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

// CartService is a service layer
@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val recipeRepository: RecipeRepository,
    private val recipeIngredientRepository: RecipeIngredientRepository
) {

    fun getCartById(cartId: Long): Cart {
        val cartEntity = cartRepository.findById(cartId)
            .orElseThrow { RuntimeException("Cart with id $cartId not found") }

        val cartItemsEntities = cartItemRepository.findByCartId(cartId)

        val cartItems = cartItemsEntities.map { itemEntity ->
            CartItem(
                id = itemEntity.id,
                cartId = itemEntity.cart.id,
                product = Product(
                    id = itemEntity.product.id,
                    name = itemEntity.product.name,
                    priceInCents = itemEntity.product.priceInCents
                )
            )
        }

        return Cart(
            id = cartEntity.id,
            totalInCents = cartEntity.totalInCents,
            items = cartItems
        )
    }

    @Transactional
    fun addRecipeToCart(cartId: Long, recipeId: Long) {
        val cartEntity = cartRepository.findById(cartId)
            .orElseThrow { RuntimeException("Cart with id $cartId not found") }

        recipeRepository.findById(recipeId)
            .orElseThrow { RuntimeException("Recipe with id $recipeId not found") }

        val ingredients = recipeIngredientRepository.findByRecipeId(recipeId)

        ingredients.forEach { ingredientEntity ->
            val cartItemEntity = CartItemEntity(
                cart = cartEntity,
                product = ingredientEntity.product
            )
            cartItemRepository.save(cartItemEntity)
        }

        val updatedCartItems = cartItemRepository.findByCartId(cartId)
        val updatedTotal = updatedCartItems.sumOf { it.product.priceInCents }
        val updatedCartEntity = cartEntity.copy(totalInCents = updatedTotal)
        cartRepository.save(updatedCartEntity)
    }

    @Transactional
    fun removeRecipeFromCart(cartId: Long, recipeId: Long) {
        val cartEntity = cartRepository.findById(cartId)
            .orElseThrow { RuntimeException("Cart with id $cartId not found") }

        recipeRepository.findById(recipeId)
            .orElseThrow { RuntimeException("Recipe with id $recipeId not found") }

        val ingredients = recipeIngredientRepository.findByRecipeId(recipeId)

        val cartItemsToRemove = cartItemRepository.findByCartId(cartId).filter { cartItem ->
            ingredients.any { it.product.id == cartItem.product.id }
        }

        cartItemRepository.deleteAll(cartItemsToRemove)

        val remainingItems = cartItemRepository.findByCartId(cartId)
        val newTotal = remainingItems.sumOf { it.product.priceInCents }
        val updatedCartEntity = cartEntity.copy(totalInCents = newTotal)
        cartRepository.save(updatedCartEntity)
    }
}
