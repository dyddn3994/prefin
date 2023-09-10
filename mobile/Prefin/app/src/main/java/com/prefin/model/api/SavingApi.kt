package com.prefin.model.api

import com.prefin.model.dto.SavingInterestSetRequest
import com.prefin.model.dto.SavingRequest
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface SavingApi {
    // 저축
    @PUT("child/{id}/deposit")
    suspend fun saving(
        @Path("id") id: Long,
        @Body savingRequest: SavingRequest,
    )

    // 인출
    @PUT("child/{id}/withdraw")
    suspend fun withdraw(
        @Path("id") id: Long,
        @Body savingRequest: SavingRequest,
    )

    // 저축내역 조회

    // 저축이율 등록
    @PUT("parent/{id}/saving")
    suspend fun savingInterestSet(
        @Path("id") id: Long,
        @Body savingInterestSetRequest: SavingInterestSetRequest,
    )
}
