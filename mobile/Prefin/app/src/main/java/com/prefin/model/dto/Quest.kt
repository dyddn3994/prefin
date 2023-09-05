package com.prefin.model.dto

data class Quest(
    var id: Long = 0L,
    var title: String = "",
    var reward: Int = 0,
    var startDate: Long = 0L,
    var endDate: Long = 0L,
    var requestStatus: Boolean = false,
)
