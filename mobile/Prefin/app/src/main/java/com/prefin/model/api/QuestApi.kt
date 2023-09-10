package com.prefin.model.api

import com.prefin.model.dto.QuestRegisterRequest
import com.prefin.model.dto.QuestCreateRequest
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
    
    
    // 자녀에게 등록된 퀘스트 조회
    // TODO: 반환값 설정 필요
    @GET("questowned/{id}")
    suspend fun childQuestList(
        @Path("id") id: Long,
    )

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
