package com.deniz.recipes.repo

import com.deniz.recipes.repo.entity.CartEntity
import org.springframework.data.jpa.repository.JpaRepository

// CartRepository interface extends from JpaRepository for DB processes.
interface CartRepository : JpaRepository<CartEntity, Long>