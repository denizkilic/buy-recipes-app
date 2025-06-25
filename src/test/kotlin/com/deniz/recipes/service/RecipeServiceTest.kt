package com.deniz.recipes.service

import com.deniz.recipes.model.Product
import com.deniz.recipes.repo.RecipeIngredientRepository
import com.deniz.recipes.repo.RecipeRepository
import com.deniz.recipes.repo.entity.ProductEntity
import com.deniz.recipes.repo.entity.RecipeEntity
import com.deniz.recipes.repo.entity.RecipeIngredientEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class RecipeServiceTest {

    @Mock
    lateinit var recipeRepository: RecipeRepository

    @Mock
    lateinit var recipeIngredientRepository: RecipeIngredientRepository

    private lateinit var service: RecipeService

    @BeforeEach
    fun setUp() {
        service = RecipeService(recipeRepository, recipeIngredientRepository)
    }

    @Test
    fun `getAllRecipes returns list of recipes with ingredients`() {
        // Given
        val recipe1 = RecipeEntity(id = 1L, name = "Recipe One")
        val recipe2 = RecipeEntity(id = 2L, name = "Recipe Two")

        val product1 = ProductEntity(id = 10L, name = "Product A", priceInCents = 100)
        val product2 = ProductEntity(id = 11L, name = "Product B", priceInCents = 200)
        val product3 = ProductEntity(id = 12L, name = "Product C", priceInCents = 300)

        val ingredient1 = RecipeIngredientEntity(id = 1L, recipe = recipe1, product = product1)
        val ingredient2 = RecipeIngredientEntity(id = 2L, recipe = recipe1, product = product2)
        val ingredient3 = RecipeIngredientEntity(id = 3L, recipe = recipe2, product = product3)

        whenever(recipeRepository.findAll()).thenReturn(listOf(recipe1, recipe2))
        whenever(recipeIngredientRepository.findByRecipeId(1L)).thenReturn(listOf(ingredient1, ingredient2))
        whenever(recipeIngredientRepository.findByRecipeId(2L)).thenReturn(listOf(ingredient3))

        // When
        val recipes = service.getAllRecipes()

        // Then
        assertEquals(2, recipes.size)

        val firstRecipe = recipes.first { it.id == 1L }
        assertEquals("Recipe One", firstRecipe.name)
        assertEquals(2, firstRecipe.ingredients.size)
        assertEquals(Product(10L, "Product A", 100), firstRecipe.ingredients[0])
        assertEquals(Product(11L, "Product B", 200), firstRecipe.ingredients[1])

        val secondRecipe = recipes.first { it.id == 2L }
        assertEquals("Recipe Two", secondRecipe.name)
        assertEquals(1, secondRecipe.ingredients.size)
        assertEquals(Product(12L, "Product C", 300), secondRecipe.ingredients[0])
    }

    @Test
    fun `getAllRecipes returns empty list when no recipes found`() {
        // Given
        whenever(recipeRepository.findAll()).thenReturn(emptyList())

        // When
        val recipes = service.getAllRecipes()

        // Then
        assertEquals(0, recipes.size)
    }
}
