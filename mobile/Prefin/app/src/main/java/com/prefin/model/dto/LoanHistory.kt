package com.prefin.model.dto

data class LoanHistory(
    var childId: Long = 0L,
    var isAccepted: Boolean = false,
    var loanAmount: Int = 0,
    var loanId: Long = 0L,
    var parentId: Long = 0L,
    var loanDate: Long = 0L,
)
