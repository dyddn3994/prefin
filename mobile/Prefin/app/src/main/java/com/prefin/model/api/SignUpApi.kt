package com.prefin.model.api

import com.prefin.model.dto.Child
import com.prefin.model.dto.Parent
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {

    // 부모 회원가입
    @POST("parent")
    suspend fun parentSignUp(@Body parent : Parent) : Response<Long>

    // 자녀 회원가입
    @POST("/child")
    suspend fun childSignUp(@Body child: Child)
}