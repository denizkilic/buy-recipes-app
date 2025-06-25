package com.deniz.recipes.repo.entity

import jakarta.persistence.*

@Entity
@Table(name = "recipe_ingredients")
data class RecipeIngredientEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    val recipe: RecipeEntity,

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    val product: ProductEntity
)
