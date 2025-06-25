package com.deniz.recipes.controller

import com.deniz.recipes.repo.ProductRepository
import com.deniz.recipes.repo.RecipeIngredientRepository
import com.deniz.recipes.repo.RecipeRepository
import com.deniz.recipes.repo.entity.ProductEntity
import com.deniz.recipes.repo.entity.RecipeEntity
import com.deniz.recipes.repo.entity.RecipeIngredientEntity

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.beans.factory.annotation.Autowired
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecipesControllerITest @Autowired constructor(
    val mockMvc: MockMvc,
    val recipeRepository: RecipeRepository,
    val productRepository: ProductRepository,
    val recipeIngredientRepository: RecipeIngredientRepository
) {

    @BeforeEach
    fun setup() {
        recipeIngredientRepository.deleteAll()
        recipeRepository.deleteAll()
        productRepository.deleteAll()
    }

    @Test
    fun `save products, recipe and recipe ingredients, then get all recipes`() {
        // Save products
        val tomato = productRepository.save(ProductEntity(name = "Tomato", priceInCents = 150))
        val pasta = productRepository.save(ProductEntity(name = "Pasta", priceInCents = 300))

        // Save recipe
        val recipe = recipeRepository.save(RecipeEntity(name = "Spaghetti Pomodoro"))

        // Link products to recipe via RecipeIngredientEntity
        recipeIngredientRepository.saveAll(
            listOf(
                RecipeIngredientEntity(recipe = recipe, product = tomato),
                RecipeIngredientEntity(recipe = recipe, product = pasta)
            )
        )

        // Call GET /recipes and expect recipe with ingredients
        mockMvc.perform(get("/recipes").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value("Spaghetti Pomodoro"))
            .andExpect(jsonPath("$[0].ingredients.length()").value(2))
            .andExpect(jsonPath("$[0].ingredients[0].name").value("Tomato"))
            .andExpect(jsonPath("$[0].ingredients[1].name").value("Pasta"))
    }
}
