package com.prefin.model.api

import com.prefin.model.dto.Child
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    // 자녀 회원가입
    @POST("/child")
    suspend fun childSignUp(@Body child: Child)

    // 자녀 로그인
    @POST("/child/login")
    suspend fun childLogin(@Body child: Child)
}
