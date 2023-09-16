package com.prefin.model.dto

import java.math.BigDecimal

data class Child(
    var account: String = "",
    var balance: Int = 0,
    var fcmToken: String? = null,
    var id: Long = 0,
    val isQuizSolved: Boolean? = false,
    var loanAmount: Int = 0,
    val mascotId: Long? = null,
    var name: String = "",
    var parentId: Long = 0,
    var password: String = "",
    val quizId: Int = 0,
    var savingAmount: Int = 0,
    val simplePass: String? = null,
    val trustScore: Int = 0,
    var userId: String = "",
    var payday: Long = 0L,
    var allowanceAmount: Int = 0,
    var savingRate: BigDecimal? = BigDecimal("0.0"),
    var loanRate: BigDecimal? = BigDecimal("0.0"),
    var possibleLoanAmount : Int = 0,
)
