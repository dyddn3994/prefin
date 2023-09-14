package com.prefin.model.dto

data class QuestOwnedQuest(
    val completed: Boolean,
    val endDate: Long,
    val questId: Int,
    val requested: Boolean,
    val reward: Int,
    val startDate: Long,
    val title: String
)