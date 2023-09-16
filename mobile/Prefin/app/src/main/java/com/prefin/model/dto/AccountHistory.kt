package com.prefin.model.dto

data class AccountHistory(
    var briefs: String = "",
    var childId: Long = 0L,
    var deposit: String = "",
    var id: Long = 0L,
    var transactionDate: String = "",
    var transactionTime: String = "",
    var withdraw: String = "",
)
