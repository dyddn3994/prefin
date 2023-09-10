package com.prefin.model.api

import com.prefin.model.dto.Child
import com.prefin.model.dto.Parent
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {


    // 자녀 로그인
    @POST("child/login")
    suspend fun childLogin(@Body child: Child) : Child


    @POST("parent/login")
    suspend fun parentLogin(@Body parent: Parent) : Parent
}
