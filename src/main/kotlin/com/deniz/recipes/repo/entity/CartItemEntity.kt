package com.deniz.recipes.repo.entity

import jakarta.persistence.*

@Entity
@Table(name = "cart_items")
data class CartItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    val cart: CartEntity,

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    val product: ProductEntity
)
