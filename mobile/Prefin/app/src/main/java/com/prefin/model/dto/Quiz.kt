package com.prefin.model.dto

data class Quiz(
    var answer: Int,
    val description: String,
    val id: Int,
    val question: String
)