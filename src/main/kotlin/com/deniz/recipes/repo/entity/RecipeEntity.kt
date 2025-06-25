package com.deniz.recipes.repo.entity

import jakarta.persistence.*

@Entity
@Table(name = "recipes")
data class RecipeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String
)
