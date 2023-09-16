package com.prefin.model.dto

data class Quiz(
    var answer: Boolean,
    val description: String,
    val id: Int,
    val question: String
)