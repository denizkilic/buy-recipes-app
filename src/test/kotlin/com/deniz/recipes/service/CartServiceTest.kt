package com.deniz.recipes.service

import com.deniz.recipes.repo.*
import com.deniz.recipes.repo.entity.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class CartServiceTest {

    @Mock
    lateinit var cartRepository: CartRepository

    @Mock
    lateinit var cartItemRepository: CartItemRepository

    @Mock
    lateinit var recipeRepository: RecipeRepository

    @Mock
    lateinit var recipeIngredientRepository: RecipeIngredientRepository

    private lateinit var service: CartService

    @BeforeEach
    fun setUp() {
        service = CartService(cartRepository, cartItemRepository, recipeRepository, recipeIngredientRepository)
    }

    @Test
    fun `getCartById returns cart with items`() {
        val cartId = 1L
        val cartEntity = CartEntity(id = cartId, totalInCents = 1000)
        val productEntity = ProductEntity(id = 10L, name = "Product1", priceInCents = 500)
        val cartItemEntity = CartItemEntity(id = 100L, cart = cartEntity, product = productEntity)

        `when`(cartRepository.findById(cartId)).thenReturn(Optional.of(cartEntity))
        `when`(cartItemRepository.findByCartId(cartId)).thenReturn(listOf(cartItemEntity))

        val cart = service.getCartById(cartId)

        assertEquals(cartId, cart.id)
        assertEquals(1000, cart.totalInCents)
        assertEquals(1, cart.items.size)
        assertEquals(productEntity.id, cart.items[0].product.id)
        assertEquals(productEntity.name, cart.items[0].product.name)
        assertEquals(productEntity.priceInCents, cart.items[0].product.priceInCents)
    }

    @Test
    fun `getCartById throws exception when cart not found`() {
        val cartId = 99L
        `when`(cartRepository.findById(cartId)).thenReturn(Optional.empty())

        val ex = assertThrows<RuntimeException> {
            service.getCartById(cartId)
        }
        assertEquals("Cart with id $cartId not found", ex.message)
    }

    @Test
    fun `addRecipeToCart adds all ingredients to cart and updates total`() {
        val cartId = 1L
        val recipeId = 2L

        val cartEntity = CartEntity(id = cartId, totalInCents = 0)
        val recipeEntity = RecipeEntity(id = recipeId, name = "RecipeName")
        val product1 = ProductEntity(id = 10L, name = "Product1", priceInCents = 300)
        val product2 = ProductEntity(id = 11L, name = "Product2", priceInCents = 700)

        val ingredient1 = RecipeIngredientEntity(id = 1L, recipe = recipeEntity, product = product1)
        val ingredient2 = RecipeIngredientEntity(id = 2L, recipe = recipeEntity, product = product2)

        `when`(cartRepository.findById(cartId)).thenReturn(Optional.of(cartEntity))
        `when`(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeEntity))
        `when`(recipeIngredientRepository.findByRecipeId(recipeId)).thenReturn(listOf(ingredient1, ingredient2))

        `when`(cartItemRepository.save(any(CartItemEntity::class.java))).thenAnswer { invocation -> invocation.arguments[0] }

        val savedCartItem1 = CartItemEntity(id = 1L, cart = cartEntity, product = product1)
        val savedCartItem2 = CartItemEntity(id = 2L, cart = cartEntity, product = product2)

        `when`(cartItemRepository.findByCartId(cartId)).thenReturn(listOf(savedCartItem1, savedCartItem2))
        `when`(cartRepository.save(any(CartEntity::class.java))).thenAnswer { invocation -> invocation.arguments[0] }

        service.addRecipeToCart(cartId, recipeId)

        verify(cartRepository).findById(cartId)
        verify(recipeRepository).findById(recipeId)
        verify(recipeIngredientRepository).findByRecipeId(recipeId)
        verify(cartItemRepository, times(2)).save(any(CartItemEntity::class.java))
        verify(cartItemRepository).findByCartId(cartId)
        verify(cartRepository).save(any(CartEntity::class.java))
    }

    @Test
    fun `addRecipeToCart throws exception when cart not found`() {
        val cartId = 99L
        val recipeId = 1L

        `when`(cartRepository.findById(cartId)).thenReturn(Optional.empty())

        val ex = assertThrows<RuntimeException> {
            service.addRecipeToCart(cartId, recipeId)
        }
        assertEquals("Cart with id $cartId not found", ex.message)
    }

    @Test
    fun `addRecipeToCart throws exception when recipe not found`() {
        val cartId = 1L
        val recipeId = 99L

        val cartEntity = CartEntity(id = cartId, totalInCents = 0)
        `when`(cartRepository.findById(cartId)).thenReturn(Optional.of(cartEntity))
        `when`(recipeRepository.findById(recipeId)).thenReturn(Optional.empty())

        val ex = assertThrows<RuntimeException> {
            service.addRecipeToCart(cartId, recipeId)
        }
        assertEquals("Recipe with id $recipeId not found", ex.message)
    }
    
    @Test
    fun `removeRecipeFromCart throws exception when cart not found`() {
        val cartId = 99L
        val recipeId = 1L

        `when`(cartRepository.findById(cartId)).thenReturn(Optional.empty())

        val ex = assertThrows<RuntimeException> {
            service.removeRecipeFromCart(cartId, recipeId)
        }
        assertEquals("Cart with id $cartId not found", ex.message)
    }

    @Test
    fun `removeRecipeFromCart throws exception when recipe not found`() {
        val cartId = 1L
        val recipeId = 99L

        val cartEntity = CartEntity(id = cartId, totalInCents = 0)
        `when`(cartRepository.findById(cartId)).thenReturn(Optional.of(cartEntity))
        `when`(recipeRepository.findById(recipeId)).thenReturn(Optional.empty())

        val ex = assertThrows<RuntimeException> {
            service.removeRecipeFromCart(cartId, recipeId)
        }
        assertEquals("Recipe with id $recipeId not found", ex.message)
    }
}
