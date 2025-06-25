package com.deniz.recipes.repo.entity

import jakarta.persistence.*

// CardEntity is entity class for DB.
@Entity
@Table(name = "carts")
data class CartEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
    val id: Long = 0,

    @Column(name = "total_in_cents", nullable = false)
    var totalInCents: Int = 0
)
