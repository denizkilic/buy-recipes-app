package com.deniz.recipes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// BuyRecipesApplication is the main class.
@SpringBootApplication
class BuyRecipesApplication

fun main(args: Array<String>) {
	runApplication<BuyRecipesApplication>(*args)
}