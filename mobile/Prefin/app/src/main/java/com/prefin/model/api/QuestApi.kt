package com.prefin.model.api

import com.prefin.model.dto.Quest
import com.prefin.model.dto.QuestCreateRequest
import com.prefin.model.dto.QuestOwned
import com.prefin.model.dto.QuestRegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface QuestApi {
    // 퀘스트 생성
    @POST("quest")
    suspend fun questCreate(
        @Body questCreateRequest: QuestCreateRequest,
    )

    // 퀘스트 삭제

    // 생성된 퀘스트 조회
    @GET("quest/{id}")
    suspend fun parentQuestItemList(
        @Path("id") id: Long,
    ): List<Quest>

    // 자녀에게 등록된 퀘스트 조회
    @GET("questowned/{id}")
    suspend fun childQuestList(
        @Path("id") id: Long,
    ): List<QuestOwned>

    // 퀘스트 등록
    @POST("questowned")
    suspend fun questRegister(
        @Body questRegisterRequest: QuestRegisterRequest,
    )

    // 퀘스트 완료 요청
    @PUT("questowned/request/{id}")
    suspend fun questFinishRequest(
        @Path("id") id: Long,
    )

    // 퀘스트 완료 처리
    @PUT("questowned/complete/{id}")
    suspend fun questFinishComplete(
        @Path("id") id: Long,
    )
}
