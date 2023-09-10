package com.prefin.model.dto

data class Quest(
    var id: Long = 0L,
    var title: String = "",
    var reward: Int = 0,
    var registered: Boolean = false,
    var parent: Parent = Parent(),
)
