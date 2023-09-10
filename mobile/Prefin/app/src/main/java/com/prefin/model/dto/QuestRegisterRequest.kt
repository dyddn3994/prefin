package com.prefin.model.dto

data class QuestRegisterRequest(
    var childId: Long = 0L,
    var endDate: Long = 0L,
    var questId: Long = 0L,
    var startDate: Long = 0L,
)
