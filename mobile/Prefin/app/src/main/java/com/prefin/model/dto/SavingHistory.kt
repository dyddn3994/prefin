package com.prefin.model.dto

data class SavingHistory(
    var id: Long = 0L,
    var savingType: String = "",
    var savingAmount: Int = 0,
    var savingDate: Long = 0L,
)
