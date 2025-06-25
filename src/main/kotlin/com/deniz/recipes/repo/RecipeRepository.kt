package com.deniz.recipes.repo

import com.deniz.recipes.repo.entity.RecipeEntity
import org.springframework.data.jpa.repository.JpaRepository

// RecipeRepository interface extends from JpaRepository for DB processes.
interface RecipeRepository : JpaRepository<RecipeEntity, Long>