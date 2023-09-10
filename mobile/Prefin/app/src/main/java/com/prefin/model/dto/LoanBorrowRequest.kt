package com.prefin.model.dto

data class LoanBorrowRequest(
    var loanAmount: Int = 0,
    var parentId: Long = 0L,
    var childId: Long = 0L,
)
