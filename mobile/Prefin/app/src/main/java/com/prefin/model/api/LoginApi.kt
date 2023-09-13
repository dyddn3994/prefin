package com.prefin.model.api

import com.prefin.model.dto.Child
import com.prefin.model.dto.Parent
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LoginApi {


    // 자녀 로그인
    @POST("child/login")
    suspend fun childLogin(@Body child: Child) : Child


    @POST("parent/login")
    suspend fun parentLogin(@Body parent: Parent) : Parent

    @PUT("parent/{id}/token")
    suspend fun parentFcmTokenRegister(@Path("id") id : Long, @Body parent : Parent) : Response<Boolean>

    @PUT("child/{id}/token")
    suspend fun childFcmTokenRegister(@Path("id") id : Long, @Body child : Child) : Response<Boolean>
}
