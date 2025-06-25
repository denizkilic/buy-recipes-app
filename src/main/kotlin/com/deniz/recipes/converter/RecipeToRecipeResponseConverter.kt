package com.deniz.recipes.converter

import com.deniz.recipes.controller.resource.*
import com.deniz.recipes.model.Recipe

object RecipeToRecipeResponseConverter {
    fun convert(recipe: Recipe): RecipeResponse {
        return RecipeResponse(
            id = recipe.id,
            name = recipe.name,
            ingredients = recipe.ingredients.map { product ->
                ProductResponse(
                    id = product.id,
                    name = product.name,
                    priceInCents = product.priceInCents
                )
            }
        )
    }
}
