package com.prefin.model.dto

data class QuestOwnedQuest(
    val completed: Boolean = false,
    val endDate: Long = 0L,
    val questId: Long = 0L,
    val requested: Boolean = false,
    val reward: Int = 0,
    val startDate: Long = 0L,
    val title: String = "",
    var questOwnedId: Long = 0L,
)
