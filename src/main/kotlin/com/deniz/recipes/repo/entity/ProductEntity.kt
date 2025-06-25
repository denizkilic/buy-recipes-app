package com.deniz.recipes.repo.entity

import jakarta.persistence.*

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    @Column(name = "price_in_cents", nullable = false)
    val priceInCents: Int
)
