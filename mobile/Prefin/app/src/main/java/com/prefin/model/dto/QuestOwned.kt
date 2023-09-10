package com.prefin.model.dto

data class QuestOwned(
    var id: Long = 0L,
    var requested: Boolean = false,
    var completed: Boolean = false,
    var startDate: Long = 0L,
    var endDate: Long = 0L,
    var quest: Quest = Quest(),
)
