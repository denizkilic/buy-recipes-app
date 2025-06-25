package com.deniz.recipes.controller

import com.deniz.recipes.controller.resource.RecipeResponse
import com.deniz.recipes.converter.RecipeToRecipeResponseConverter
import com.deniz.recipes.service.RecipeService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// BuyRecipesController is controller class which is consists of Rest API endpoints.
@RestController
@RequestMapping("/recipes")
class RecipesController(private val recipeService: RecipeService) {

    private val logger = LoggerFactory.getLogger(RecipesController::class.java)

    @GetMapping
    fun getAllRecipes(): List<RecipeResponse> {
        logger.info("Fetching all recipes")

        val recipes = recipeService.getAllRecipes()
        return recipes.map { RecipeToRecipeResponseConverter.convert(it) }
    }
}

