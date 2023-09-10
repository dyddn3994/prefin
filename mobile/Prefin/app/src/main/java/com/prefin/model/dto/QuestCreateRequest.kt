package com.prefin.model.dto

data class QuestCreateRequest(
    var parentId: Long = 0L,
    var reward: Int = 0,
    var title: String = "",
)
