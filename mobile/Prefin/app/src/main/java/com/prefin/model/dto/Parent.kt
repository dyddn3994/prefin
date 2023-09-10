package com.prefin.model.dto

import java.math.BigDecimal

data class Parent(
    var account: String?,
    var balance: Int = 0,
    var fcmToken: String?,
    val id: Long = 0,
    var loanRate: BigDecimal?,
    var name: String = "",
    var password: String = "",
    var savingRate: BigDecimal?,
    var simplePass: String?,
    var userId: String = ""
) {
    constructor(): this(null, 0, null, 0, null, "", "", null, null, "")
}