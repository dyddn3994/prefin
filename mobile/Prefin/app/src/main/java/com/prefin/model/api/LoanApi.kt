package com.prefin.model.api

import com.prefin.model.dto.LoanBorrowRequest
import com.prefin.model.dto.LoanGiveMoneyRequest
import com.prefin.model.dto.LoanInterestSetRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LoanApi {
    // 대출이율 등록
    @PUT("parent/{id}/loan")
    suspend fun loanInterestSet(
        @Path("id") id: Long,
        @Body loanInterestSetRequest: LoanInterestSetRequest,
    )

    // 대출
    @POST("loan/askForMoney")
    suspend fun askForMoney(
        @Body loanBorrowRequest: LoanBorrowRequest,
    )

    // 대출 허락
    @POST("loan/giveMoney")
    suspend fun giveMoney(
        @Body loanGiveMoneyRequest: LoanGiveMoneyRequest,
    )
}
