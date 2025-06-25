package com.deniz.recipes.repo

import com.deniz.recipes.repo.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

//ProductRepository interface extends from JpaRepository for DB processes.
interface ProductRepository : JpaRepository<ProductEntity, Long>