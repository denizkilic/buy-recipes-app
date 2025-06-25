package com.deniz.recipes.service

import com.deniz.recipes.model.Product
import com.deniz.recipes.model.Recipe
import com.deniz.recipes.repo.RecipeIngredientRepository
import com.deniz.recipes.repo.RecipeRepository
import org.springframework.stereotype.Service

//RecipeService is service layer for recipes
@Service
class RecipeService(
    private val recipeRepository: RecipeRepository,
    private val recipeIngredientRepository: RecipeIngredientRepository
) {

    fun getAllRecipes(): List<Recipe> {
        val recipeEntities = recipeRepository.findAll()

        return recipeEntities.map { recipeEntity ->
            val ingredientEntities = recipeIngredientRepository.findByRecipeId(recipeEntity.id)

            val ingredients = ingredientEntities.map { ingredientEntity ->
                val product = ingredientEntity.product
                Product(
                    id = product.id,
                    name = product.name,
                    priceInCents = product.priceInCents
                )
            }

            Recipe(
                id = recipeEntity.id,
                name = recipeEntity.name,
                ingredients = ingredients
            )
        }
    }
}


