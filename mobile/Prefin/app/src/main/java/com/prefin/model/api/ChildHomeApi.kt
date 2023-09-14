package com.prefin.model.api

import com.prefin.model.dto.Child
import retrofit2.http.GET
import retrofit2.http.Path

interface ChildHomeApi {

    @GET("child/{id}")
    suspend fun getChildData(@Path("id") id : Long) : Child
}
