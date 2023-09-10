package com.prefin.model.api

import com.prefin.model.dto.PinMoneySetRequest
import com.prefin.model.dto.PinMoneyTransferRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface PinMoneyApi {
    // 정기 용돈 등록
    @POST("allowance/set")
    suspend fun pinMoneySet(@Body pinMoneySetRequest: PinMoneySetRequest)

    // 용돈 이체
    @POST("allowance/transfer")
    suspend fun pinMoneyTransfer(@Body pinMoneyTransferRequest: PinMoneyTransferRequest)
}
