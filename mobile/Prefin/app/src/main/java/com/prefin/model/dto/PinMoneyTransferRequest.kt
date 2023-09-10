package com.prefin.model.dto

data class PinMoneyTransferRequest(
    var parentId: Long = 0L,
    var childId: Long = 0L,
    var allowanceAmount: Int = 0,
)
