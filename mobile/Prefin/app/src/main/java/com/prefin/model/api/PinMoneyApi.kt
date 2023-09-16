package com.prefin.model.api

import com.prefin.model.dto.AccountHistory
import com.prefin.model.dto.PinMoneySetRequest
import com.prefin.model.dto.PinMoneyTransferRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PinMoneyApi {
    // 정기 용돈 등록
    @POST("allowance/set")
    suspend fun pinMoneySet(@Body pinMoneySetRequest: PinMoneySetRequest)

    // 용돈 이체
    @POST("allowance/transfer")
    suspend fun pinMoneyTransfer(@Body pinMoneyTransferRequest: PinMoneyTransferRequest)

    // 계좌 내역 조회
    @GET("child/{id}/accounthistory")
    suspend fun getAccountHistory(
        @Path("id") childId: Long,
    ): List<AccountHistory>
}
