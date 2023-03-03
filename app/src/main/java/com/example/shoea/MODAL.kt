package com.example.shoea

data class MODAL(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)