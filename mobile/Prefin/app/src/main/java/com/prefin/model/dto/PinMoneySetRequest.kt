package com.prefin.model.dto

data class PinMoneySetRequest(
    var payday: Long = 0L,
    var allowanceAmount: Int = 0,
    var parentId: Long = 0L,
    var childId: Long = 0L,
)
