package com.prefin.model.dto

data class Quiz(
    var answer: Boolean,
    val description: String = "퀴즈가 없어요",
    val id: Int,
    val question: String = "더 이상 퀴즈가 없습니다."
){
    constructor() : this(false, "퀴즈가 없어요", 0, "퀴즈가 없어요")
}