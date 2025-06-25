package com.deniz.recipes.exceptions

import java.util.Objects

// BaseVndException is class for custom exceptions
open class BaseVndException(
    val field: String = "",
    val code: String,
    override val message: String
) : RuntimeException(message) {

    init {
        Objects.requireNonNull(code) { "Error code cannot be null" }
    }
}