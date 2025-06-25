package com.deniz.recipes.repo

import com.deniz.recipes.repo.entity.RecipeIngredientEntity
import org.springframework.data.jpa.repository.JpaRepository

//RecipeIngredientRepository interface extends from JpaRepository for DB processes.
interface RecipeIngredientRepository : JpaRepository<RecipeIngredientEntity, Long> {
    fun findByRecipeId(recipeId: Long): List<RecipeIngredientEntity>
}