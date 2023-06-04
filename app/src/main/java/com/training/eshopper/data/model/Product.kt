package com.training.eshopper.data.model

data class Product(
    val id: Int,
    val name: String,
    val idUser: String,
    val image: String,
    val price: Long,
    val detail: String
)