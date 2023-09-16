package com.prefin.model.dto

import java.math.BigDecimal

data class Parent(
    var account: String? = "",
    var balance: Int = 0,
    var fcmToken: String? = "",
    var id: Long = 0,
    var loanRate: BigDecimal? = BigDecimal("0.0"),
    var name: String = "",
    var password: String = "",
    var savingRate: BigDecimal? = BigDecimal("0.0"),
    var simplePass: String? = "",
    var userId: String = "",
    var maxSavingAmount: Int = 0,
) {
    constructor(): this(null, 0, null, 0, null, "", "", null, null, "")
}