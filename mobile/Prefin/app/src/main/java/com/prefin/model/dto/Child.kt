package com.prefin.model.dto

data class Child(
    val account: String = "",
    val balance: Int = 0,
    val fcmToken: String? = null,
    val id: Long = 0,
    val isQuizSolved: Boolean? = false,
    val loanAccount: Int = 0,
    val mascotId: Long? = null,
    var name: String = "",
    var parentId: Long = 0,
    var password: String = "",
    val quizId: Int = 0,
    val savingAmount: Int = 0,
    val simplePass: String? = null,
    val trustScore: Int = 0,
    var userId: String = ""
)