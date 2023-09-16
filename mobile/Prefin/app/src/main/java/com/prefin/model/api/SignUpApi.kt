package com.prefin.model.api

import com.prefin.model.dto.Child
import com.prefin.model.dto.Parent
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SignUpApi {

    // 부모 회원가입
    @POST("parent")
    suspend fun parentSignUp(@Body parent: Parent): Response<Long>

    // 자녀 회원가입
    @POST("child")
    suspend fun childSignUp(@Body child: Child): Long

    @PUT("parent/{id}/account")
    suspend fun accountRegister(@Path("id") id: Long, @Body parent: Parent): Response<Boolean>

    @PUT("child/{id}/account")
    suspend fun childAccountRegister(@Path("id") id: Long, @Body child: Child): Response<Boolean>

    // 간편 비밀번호 등록
    @PUT("parent/{id}/simplepass")
    suspend fun parentSimplePassRegister(@Path("id") id: Long, @Body parent: Parent): Response<Boolean>
    @PUT("child/{id}/simplepass")
    suspend fun childSimplePassRegister(@Path("id") id: Long, @Body child: Child): Response<Boolean>
}
