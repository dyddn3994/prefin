package com.prefin.model.api

import com.prefin.model.dto.SavingHistory
import com.prefin.model.dto.Parent
import com.prefin.model.dto.SavingInterestSetRequest
import com.prefin.model.dto.SavingRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
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
    @GET("savinghistory/{id}")
    suspend fun getSavingHistory(
        @Path("id") childId: Long,
    ): List<SavingHistory>

    // 저축이율 등록
    @PUT("parent/{id}/saving")
    suspend fun savingInterestSet(
        @Path("id") id: Long,
        @Body savingInterestSetRequest: SavingInterestSetRequest,
    )

    @PUT("parent/{id}/maxSaving")
    suspend fun maxSaving(
        @Path("id") id : Long,
        @Body parent : Parent
    ) : Response<Boolean>
}
