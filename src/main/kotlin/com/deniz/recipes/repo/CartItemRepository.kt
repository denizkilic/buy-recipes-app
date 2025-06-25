package com.deniz.recipes.repo

import com.deniz.recipes.repo.entity.CartItemEntity
import org.springframework.data.jpa.repository.JpaRepository

// CartItemRepository interface extends from JpaRepository for DB processes.
interface CartItemRepository : JpaRepository<CartItemEntity, Long> {
    fun findByCartId(cartId: Long): List<CartItemEntity>
}

